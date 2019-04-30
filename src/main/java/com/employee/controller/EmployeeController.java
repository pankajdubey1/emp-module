package com.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.model.Employee;
import com.employee.service.EmployeeService;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@PostMapping
	public String addEmployeeToQueue(@RequestBody Employee emp) {
		employeeService.sendMessageToQueue(emp);
		return "Details been pushed to RabbitMQ";
	}

	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public List<Employee> getEmployees() {
		return employeeService.getEmployees();
	}

}
