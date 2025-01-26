package com.payroll.payment;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PAYMENT")
public class Payment {

	private @Id @GeneratedValue Long id;

	private String grossPay;
	private Status status;

	Payment() {
	}

	Payment(String grossPay, Status status) {
		this.grossPay = grossPay;
		this.status = status;
	}

	public Long getId() {
		return this.id;
	}

	public String getGrossPay() {
		return this.grossPay;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setGrossPay(String grossPay) {
		this.grossPay = grossPay;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object p) {

		if (this == p)
			return true;
		if (!(p instanceof Payment))
			return false;
		Payment payment = (Payment) p;
		return Objects.equals(this.id, payment.id) && Objects.equals(this.grossPay, payment.grossPay)
				&& this.status == payment.status;
	}

	public int hashCode() {
		return Objects.hash(this.id, this.grossPay, this.status);
	}

	@Override
	public String toString() {
		return "Payment{" + "id=" + this.id + ", grossPay=" + this.grossPay + '\'' + ", status=" + this.status + "}";
	}
}
