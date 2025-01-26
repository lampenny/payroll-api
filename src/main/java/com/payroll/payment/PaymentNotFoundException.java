package com.payroll.payment;

class PaymentNotFoundException extends RuntimeException {
	PaymentNotFoundException(Long id) {
		super("Could not find payment " + id);
	}
}
