package com.payroll.payment;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.hateoas.server.RepresentationModelAssembler;

@Component
class PaymentModelAssembler implements RepresentationModelAssembler<Payment, EntityModel<Payment>> {

	@Override
	public EntityModel<Payment> toModel(Payment payment) {

		EntityModel<Payment> paymentModel = EntityModel.of(payment,
				linkTo(methodOn(PaymentController.class).one(payment.getId())).withSelfRel(),
				linkTo(methodOn(PaymentController.class).all()).withRel("payments"));

		if (payment.getStatus() == Status.IN_PROGRESS) {
			paymentModel.add(linkTo(methodOn(PaymentController.class).cancel(payment.getId())).withRel("cancel"));
			paymentModel.add(linkTo(methodOn(PaymentController.class).complete(payment.getId())).withRel("complete"));
		}

		return paymentModel;
	}
}
