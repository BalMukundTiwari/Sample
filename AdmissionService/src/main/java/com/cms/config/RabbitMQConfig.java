package com.cms.config;

import java.io.IOException;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.client.ConnectionFactory;

@Configuration
public class RabbitMQConfig {
	@Value("${spring.rabbitmq.queue-name}")
	private String queueName; 

	@Value("${spring.rabbitmq.host}")
	private String host; // RabbitMQ server host

	@Value("${spring.rabbitmq.port}")
	private int port; // RabbitMQ server port

	@Value("${spring.rabbitmq.username}")
	private String userName; // RabbitMQ server username

	@Value("${spring.rabbitmq.password}")
	private String password; // RabbitMQ server password

	@Value("${spring.rabbitmq.virtual-host}")
	private String virtualHost; // RabbitMQ server virtual host

	public ConnectionFactory connectionFactory() throws IOException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(host);
		factory.setPort(port);
		factory.setUsername(userName);
		factory.setPassword(password);
		factory.setVirtualHost(virtualHost);
		return factory;
	}

	@Bean
	public Queue queue() {
		return new Queue(queueName);
	}
}