package br.com.fiap.app;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

import br.com.fiap.business.TweetBusiness;
import br.com.fiap.entity.Tweet;
import br.com.fiap.route.SimpleRouteBuilder;
import br.com.fiap.util.Autenticacao;
import twitter4j.Twitter;

public class Aplicacao {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Autenticacao aut = new Autenticacao();
		Twitter twitter = aut.retornaTwitter();
		TweetBusiness tweetBusiness = new TweetBusiness(twitter);
		List<Tweet> tweets = new ArrayList<Tweet>();

		try {
			FileWriter arq = new FileWriter("C:/Users/Melina Carniel/Desktop/InputFolder/twitter.txt");
			PrintWriter gravarArq = new PrintWriter(arq);
			
			tweets = tweetBusiness.buscarTweets(twitter, "#java");

			for (Tweet tweet : tweets) {
				gravarArq.printf("Autor: " + tweet.getNomeAutor()+"\n");
				gravarArq.printf("Mensagem: " + tweet.getMensagem()+"\n");
				gravarArq.printf("------------------------------------------------------------------\n");
			}
			
			arq.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		SimpleRouteBuilder routeBuilder = new SimpleRouteBuilder();
		CamelContext ctx = new DefaultCamelContext();
		
		
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://0.0.0.0:61616");
		ctx.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		
		try {
			ctx.addRoutes(routeBuilder);
			ctx.start();
			Thread.sleep(5*6*1000);
			ctx.stop();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

}
