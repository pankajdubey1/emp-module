package com.employee.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.employee.model.Employee;
import com.employee.service.impl.EmployeeServiceImpl;

@SpringBootTest
public class EmployeeControllerTest {

	@InjectMocks
	private EmployeeController employeeController;

	@Mock
	private EmployeeServiceImpl employeeService;

	@Test
	public void testAddEmployeeToQueue() {
		Employee employee = getEmployee();
		employeeController.addEmployeeToQueue(employee);
		Mockito.verify(employeeService).sendMessageToQueue(employee);
	}

	@Test
	public void testGetEmployees() {
		List<Employee> expected = list();
		Mockito.when(employeeService.getEmployees()).thenReturn(expected);
		List<Employee> actual = employeeController.getEmployees();
		assertEquals(expected, actual);
	}

	private List<Employee> list() {
		List<Employee> list = new ArrayList<Employee>();
		list.add(getEmployee());
		return list;
	}

	private Employee getEmployee() {
		Employee employee = new Employee();
		employee.setEmpId(101);
		employee.setEmpName("empName");
		return employee;
	}
}
