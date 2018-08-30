package br.com.fiap.route;

import org.apache.camel.builder.RouteBuilder;

public class SimpleRouteBuilder extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		from("file:/Users/Melina Carniel/Desktop/InputFolder").split().tokenize("\n").to("jms:queue:twitter");
	}

}
