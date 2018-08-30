package br.com.fiap.consumer;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class ReadQueue {

	public static void main(String[] args) throws Exception {

		CamelContext ctx = new DefaultCamelContext();
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		
		ctx.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		
		ctx.addRoutes(new RouteBuilder() {
			
			@Override
			public void configure() {
				from ("jms:twitter")
				.to("stream:out")
				.to("file:/Users/Melina Carniel/Desktop/outputFolder");
				
			}

			
		});
		
		ctx.start();
		Thread.sleep(10000);
		ctx.stop();

	}

}
