package com.payroll.payment;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PaymentController {
	private final PaymentRepository paymentRepository;
	private final PaymentModelAssembler assembler;

	PaymentController(PaymentRepository paymentRepository, PaymentModelAssembler assembler) {

		this.paymentRepository = paymentRepository;
		this.assembler = assembler;
	}

	@GetMapping("/payments")
	CollectionModel<EntityModel<Payment>> all() {

		List<EntityModel<Payment>> payments = paymentRepository.findAll().stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());

		return CollectionModel.of(payments,
				linkTo(methodOn(PaymentController.class).all()).withSelfRel());
	}

	@GetMapping("/payments/{id}")
	EntityModel<Payment> one(@PathVariable Long id) {

		Payment payment = paymentRepository.findById(id)
				.orElseThrow(() -> new PaymentNotFoundException(id));

		return assembler.toModel(payment);
	}

	@PostMapping("/payments")
	ResponseEntity<EntityModel<Payment>> newPayment(@RequestBody Payment payment) {

		payment.setStatus(Status.IN_PROGRESS);
		Payment newPayment = paymentRepository.save(payment);

		return ResponseEntity
				.created(linkTo(methodOn(PaymentController.class).one(newPayment.getId())).toUri())
				.body(assembler.toModel(newPayment));
	}

	@DeleteMapping("/payments/{id}/cancel")
	ResponseEntity<?> cancel(@PathVariable Long id) {

		Payment payment = paymentRepository.findById(id)
				.orElseThrow(() -> new PaymentNotFoundException(id));

		if (payment.getStatus() == Status.IN_PROGRESS) {
			payment.setStatus(Status.CANCELLED);
			return ResponseEntity.ok(assembler.toModel(paymentRepository.save(payment)));
		}

		return ResponseEntity
				.status(HttpStatus.METHOD_NOT_ALLOWED)
				.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
				.body(Problem.create()
						.withTitle("Method not allowed")
						.withDetail("You can't cancel a payment that is in the " + payment.getStatus() + " status"));
	}

	@PutMapping("/payments/{id}/complete")
	ResponseEntity<?> complete(@PathVariable Long id) {

		Payment payment = paymentRepository.findById(id)
				.orElseThrow(() -> new PaymentNotFoundException(id));

		if (payment.getStatus() == Status.IN_PROGRESS) {
			payment.setStatus(Status.COMPLETED);
			return ResponseEntity.ok(assembler.toModel(paymentRepository.save(payment)));
		}

		return ResponseEntity
				.status(HttpStatus.METHOD_NOT_ALLOWED)
				.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
				.body(Problem.create()
						.withTitle("Method not allowed")
						.withDetail("You can't complete an order that is in the " + payment.getStatus() + " status"));
	}
}
