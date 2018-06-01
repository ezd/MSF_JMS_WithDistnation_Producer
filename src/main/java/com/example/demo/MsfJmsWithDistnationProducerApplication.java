package com.example.demo;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MsfJmsWithDistnationProducerApplication {
	
	@Autowired
	JmsTemplate jmsTemplate; 
	
	String msgResponse;
	
	public static void main(String[] args) {
		SpringApplication.run(MsfJmsWithDistnationProducerApplication.class, args);
	}
	
	@RequestMapping("/sar/{message}")
	public void send(@PathVariable("message") final String msg,HttpServletResponse response) {
		//sending
		this.jmsTemplate.convertAndSend("order-queue", msg);
		try {
			response.sendRedirect("/sar");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping("/sar")
	public String receive() {
		//receiving
		String messageReceived=this.msgResponse;
		
		return "success with "+messageReceived;
	}
	
	@JmsListener(destination="order-queue-response")
	private void getMessage(String msg) {
		this.msgResponse=msg;
	}
	
}
