package com.lianggzone.rabbitmq.config;

import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
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
    Queue queue() {
        String name = env.getProperty("mq.queue").trim();
        // 是否持久化
        boolean durable = StringUtils.isNotBlank(env.getProperty("mq.queue.durable").trim())?
                Boolean.valueOf(env.getProperty("mq.queue.durable").trim()) : true; 
        // 仅创建者可以使用的私有队列，断开后自动删除
        boolean exclusive = StringUtils.isNotBlank(env.getProperty("mq.queue.exclusive").trim())?
                Boolean.valueOf(env.getProperty("mq.queue.exclusive").trim()) : false; 
        // 当所有消费客户端连接断开后，是否自动删除队列
        boolean autoDelete = StringUtils.isNotBlank(env.getProperty("mq.queue.autoDelete").trim())?
                Boolean.valueOf(env.getProperty("mq.queue.autoDelete").trim()) : false; 
        return new Queue(name, durable, exclusive, autoDelete);
    }
	
    @Bean
    TopicExchange exchange() {
        String name = env.getProperty("mq.exchange").trim();
        // 是否持久化
        boolean durable = StringUtils.isNotBlank(env.getProperty("mq.exchange.durable").trim())?
                Boolean.valueOf(env.getProperty("mq.exchange.durable").trim()) : true;
        // 当所有消费客户端连接断开后，是否自动删除队列
        boolean autoDelete = StringUtils.isNotBlank(env.getProperty("mq.exchange.autoDelete").trim())?
                Boolean.valueOf(env.getProperty("mq.exchange.autoDelete").trim()) : false;
        return new TopicExchange(name, durable, autoDelete);
    }
    
    @Bean
    Binding binding() {
        String routekey = env.getProperty("mq.routekey").trim();
        return BindingBuilder.bind(queue()).to(exchange()).with(routekey);
    }
}
