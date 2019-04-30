package com.employee.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import com.employee.model.Employee;
import com.employee.repository.EmployeeRepository;

@SpringBootTest
public class EmployeeServiceTest {

	@InjectMocks
	private EmployeeServiceImpl employeeService;

	@Mock
	private AmqpTemplate rabbitTemplate;

	@Mock
	private Environment env;

	@Mock
	private EmployeeRepository employeeRepository;

	@Test
	public void testSendMessageToQueue() {
		Employee employee = getEmployee();
		employeeService.sendMessageToQueue(employee);
		verify(rabbitTemplate).convertAndSend(null, null, employee);
	}

	@Test
	public void testRecieveMessageFromQueue() {
		Employee employee = getEmployee();
		employeeService.recieveMessageFromQueue(employee);
		verify(employeeRepository).save(employee);
	}

	@Test
	public void testGetEmployees() {
		List<Employee> expected = list();
		when(employeeRepository.findAll()).thenReturn(expected);
		List<Employee> actual = employeeService.getEmployees();
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
