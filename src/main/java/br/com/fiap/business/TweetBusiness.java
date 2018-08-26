package br.com.fiap.business;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.entity.Tweet;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TweetBusiness {
	Twitter twitter;
	/**
	 * Metodo construtor
	 */
	public TweetBusiness(Twitter twitter) {
		this.twitter = twitter;
	}
	
	public List<Tweet> buscarTweets(Twitter twitter, String hashtag) throws TwitterException {

		LocalDate agora = LocalDate.now().minusDays(1);
		LocalDate dtDataInicio = agora.minusDays(6);
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String dataFim = agora.format(formatador);
		String dataInicio = dtDataInicio.format(formatador);

		System.out.println("Data inicio: "+dataInicio+" - Data fim: " + dataFim);

		Query query = new Query(hashtag);
		query.setLang("pt");
		query.setSince(dataInicio);
		query.setUntil(dataFim);
		QueryResult result;
		List<Tweet> tweets = new ArrayList<Tweet>();

		result = twitter.search(query);

		while (result.hasNext()) {
			query = result.nextQuery();

			for (Status status : result.getTweets()) {
				Tweet tweet = new Tweet();
				tweet.setData(status.getCreatedAt());
				tweet.setNomeAutor(status.getUser().getName());
				tweet.setNomeUsuario(status.getUser().getScreenName());
				tweet.setQtdFavoritos(status.getFavoriteCount());
				tweet.setQtdRetweets(status.getRetweetCount());
				tweet.setMensagem(status.getText());
				tweets.add(tweet);

			}

			result = twitter.search(query);

		}

		// acredito que nao precisamos retornar nada, ja que criamos o relatorio
		return tweets;
	}
}
