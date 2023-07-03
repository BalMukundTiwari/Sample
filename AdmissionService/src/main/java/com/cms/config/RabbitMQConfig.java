package com.cms.config;

import java.io.IOException;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;




public class RabbitMQConfig
{
	
	private String queueName;
	
	
	private String host;
	
	
	private int port;
	
	
	private String userName;
	
	
	private String password;
	
	
	private String virtualHost;




	public ConnectionFactory connectionFactory() throws IOException
	{
		return null;
	}

	public Queue queue()
	{
		return null;
	}

	
	
	
	
	
}
