package com.cms.config;

import java.io.IOException;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.FanoutExchange;


public class RabbitMQConfig
{
	@Value("${spring.rabbitmq.queueName}")
	private String queueName;
	
	@Value("${spring.rabbitmq.host}")
	private String host;
	
	@Value("${spring.rabbitmq.port}")
	private int port;
	
	@Value("${spring.rabbitmq.userName}")
	private String userName;
	
	@Value("${spring.rabbitmq.password}")
	private String password;
	
	@Value("${spring.rabbitmq.virtualHost}")
	private String virtualHost;



	@Bean
	public ConnectionFactory connectionFactory() throws IOException
	{
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        return connectionFactory;
	}

	@Bean
	public Queue queue()
	{
		return new Queue(queueName,false);
	}

	@Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }

	@Bean
	public Declarables fanoutBindings() {
	    Queue fanoutQueue1 = new Queue("fanout.queue1", false);
//	    Queue fanoutQueue2 = new Queue("fanout.queue2", false);
	    FanoutExchange fanoutExchange = new FanoutExchange("fanout.exchange");

	    return new Declarables(
	      fanoutQueue1,
//	      fanoutQueue2,
	      fanoutExchange,
	      BindingBuilder.bind(fanoutQueue1).to(fanoutExchange));
//	      BindingBuilder.bind(fanoutQueue2).to(fanoutExchange));
	}
	
	
	
	
}
