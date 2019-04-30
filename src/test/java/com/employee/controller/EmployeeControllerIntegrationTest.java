package com.employee.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.employee.EmpModuleApplication;
import com.employee.model.Employee;

@SpringBootTest(classes = EmpModuleApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIntegrationTest {

	@LocalServerPort
	private int port;

	private TestRestTemplate restTemplate = new TestRestTemplate();

	private HttpHeaders headers = new HttpHeaders();

	@Test
	public void testAddEmployeeToQueue() {
		ResponseEntity<String> response = getResponse(getEmployee(), "/employees", HttpMethod.POST);
		HttpStatus expected = HttpStatus.OK;
		HttpStatus actual = response.getStatusCode();
		assertEquals(expected, actual);
	}

	@Test
	public void testGetEmployees() {
		ResponseEntity<String> response = getResponse(null, "/employees", HttpMethod.GET);
		HttpStatus expected = HttpStatus.OK;
		HttpStatus actual = response.getStatusCode();
		assertEquals(expected, actual);
	}

	private Employee getEmployee() {
		Employee employee = new Employee();
		employee.setEmpId(101);
		employee.setEmpName("empName");
		return employee;
	}

	private ResponseEntity<String> getResponse(Employee dto, String uri, HttpMethod method) {
		HttpEntity<Employee> entity = new HttpEntity<Employee>(dto, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort(uri), method, entity, String.class);
		return response;
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
}
