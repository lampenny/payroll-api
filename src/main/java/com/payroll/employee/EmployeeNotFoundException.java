package com.payroll.employee;

public class EmployeeNotFoundException extends RuntimeException {
	EmployeeNotFoundException(Long id) {
		super("Could not find employee " + id);
	}
}
