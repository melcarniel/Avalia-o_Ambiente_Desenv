package br.com.fiap.route;

import org.apache.camel.builder.RouteBuilder;

public class SimpleRouteBuilder extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		from("D:\\inputFolder\twitter.txt").split().tokenize("\n").to("jms:queue:twitter");
	}

}
