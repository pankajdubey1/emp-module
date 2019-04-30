package com.employee.service.impl;

import java.util.List;

import com.employee.model.Employee;
import com.employee.repository.EmployeeRepository;
import com.employee.service.EmployeeService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private Environment env;

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public void sendMessageToQueue(Employee employee) {
		rabbitTemplate.convertAndSend(env.getProperty("rabbitmq.exchange"), env.getProperty("rabbitmq.routingkey"),
				employee);
		System.out.println("Msg been pushed to RabbitMQ: " + employee);
	}

	@RabbitListener(queues = "${rabbitmq.queue}")
	public void recieveMessageFromQueue(Employee employee) {
		employeeRepository.save(employee);
		System.out.println("Recieved Message From RabbitMQ: " + employee);
	}

	@Override
	public List<Employee> getEmployees() {
		return employeeRepository.findAll();
	}
}
