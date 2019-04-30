package com.employee.service;

import com.employee.model.Employee;

import java.util.List;

public interface EmployeeService {

	void sendMessageToQueue(Employee emp);

	List<Employee> getEmployees();
}
