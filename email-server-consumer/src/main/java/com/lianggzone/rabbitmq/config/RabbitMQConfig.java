package com.lianggzone.rabbitmq.config;

import com.lianggzone.rabbitmq.component.MailMessageListenerAdapter;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * <h3>概要:</h3><p>RabbitMQConfig</p>
 * <h3>功能:</h3><p>spring bean初始化</p>
 * <h3>履历:</h3>
 * <li>2017年6月8日  v0.1 版本内容: 新建</li>
 * @author 粱桂钊
 * @since 0.1
 */
@Configuration
@ComponentScan(basePackages = {"com.lianggzone.rabbitmq"})
@PropertySource(value = {"classpath:application.properties"})
public class RabbitMQConfig {

	@Autowired
	private Environment env;

	@Bean
	public ConnectionFactory connectionFactory() throws Exception {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost(env.getProperty("mq.host").trim());
		connectionFactory.setPort(Integer.parseInt(env.getProperty("mq.port").trim()));
		connectionFactory.setVirtualHost(env.getProperty("mq.vhost").trim());
		connectionFactory.setUsername(env.getProperty("mq.username").trim());
		connectionFactory.setPassword(env.getProperty("mq.password").trim());
		return connectionFactory;
	}
	
	@Bean
	public CachingConnectionFactory cachingConnectionFactory() throws Exception {
		return new CachingConnectionFactory(connectionFactory());
	}
	
	@Bean
	public RabbitTemplate rabbitTemplate() throws Exception {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory());
		rabbitTemplate.setChannelTransacted(true);
		return rabbitTemplate;
	}
	
	@Bean
	public AmqpAdmin amqpAdmin() throws Exception {
		return new RabbitAdmin(cachingConnectionFactory());
	}

	@Bean
	public SimpleMessageListenerContainer listenerContainer(
			@Qualifier("mailMessageListenerAdapter") MailMessageListenerAdapter mailMessageListenerAdapter) throws Exception {
		String queueName = env.getProperty("mq.queue").trim();

		SimpleMessageListenerContainer simpleMessageListenerContainer =
				new SimpleMessageListenerContainer(cachingConnectionFactory());
		simpleMessageListenerContainer.setQueueNames(queueName);
		simpleMessageListenerContainer.setMessageListener(mailMessageListenerAdapter);
		// 设置手动 ACK
		simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		return simpleMessageListenerContainer;
	}
}
