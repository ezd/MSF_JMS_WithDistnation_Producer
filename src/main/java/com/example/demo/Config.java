package com.example.demo;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@EnableJms
public class Config {
	@Bean
	ActiveMQConnectionFactory activeMQConnectionFactory() {
		ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory("tcp://localhost:61616");
		return activeMQConnectionFactory;
	}
	@Bean
	JmsTemplate jmsTemplate() {
		JmsTemplate jmsTemplate=new JmsTemplate(activeMQConnectionFactory());
		jmsTemplate.setDefaultDestinationName("order-queue");
		return jmsTemplate;
	}
	@Bean
	DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory() {
		DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory=new DefaultJmsListenerContainerFactory();
		defaultJmsListenerContainerFactory.setConnectionFactory(activeMQConnectionFactory());
		defaultJmsListenerContainerFactory.setConcurrency("1-1");
		return defaultJmsListenerContainerFactory;
	}

}
