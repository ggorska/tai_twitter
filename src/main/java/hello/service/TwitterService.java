package hello.service;

import hello.model.TweetList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import java.net.URI;
import java.net.URISyntaxException;

//@Slf4j - logger
@Service
public class TwitterService {
//    public static final String APP_URI = "https://taiproject2017.herokuapp.com";
    public static final String APP_URI = "localhost:8080";
    public static final String LOGIN_FAILED = APP_URI + "/loginfailed";
    private static final String CONSUMER_KEY = "NuiqWe8qGEBUeYLKoswgzOqVm";
    private static final String CONSUMER_SECRET = "mSZUhCoPCNwfv3yK6HkiXtmvVZlPvT9pnxEpzo8J02mnDajcPt";
    private RequestToken requestToken;

    private Twitter twitter;

    @Autowired private TweetList tweetList;

    TwitterService() {
        ConfigurationBuilder conf = new ConfigurationBuilder();
        conf.setDebugEnabled(true)
                .setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_SECRET)
                .setOAuthAccessToken("862343971678425090-hYXhndkWdoUHal54zI2ZT25hGpoEdjS")
                .setOAuthAccessTokenSecret("y9802EE0lYp5Sn5nTXOuGpFrzQwQjQGldqBvx1xxB17dw");
        twitter = new TwitterFactory(conf.build()).getInstance();
    }

    /*
    public String getOAuthRequestToken() {
        ConfigurationBuilder conf = new ConfigurationBuilder();
        conf.setDebugEnabled(true)
                .setOAuthConsumerKey("NuiqWe8qGEBUeYLKoswgzOqVm")
                .setOAuthConsumerSecret("mSZUhCoPCNwfv3yK6HkiXtmvVZlPvT9pnxEpzo8J02mnDajcPt")
                .setOAuthAccessToken("862343971678425090-hYXhndkWdoUHal54zI2ZT25hGpoEdjS")
                .setOAuthAccessTokenSecret("y9802EE0lYp5Sn5nTXOuGpFrzQwQjQGldqBvx1xxB17dw");

        System.out.println("Successfully updated the status ");
        return "token";
    }
    */

    public String getUserName() {
        try {
            return twitter.getScreenName();
        } catch (TwitterException e) {
            e.printStackTrace();
            return "unknown";
        }

    }

    public URI getLoginUri() throws URISyntaxException {
        try {
            requestToken = twitter.getOAuthRequestToken(APP_URI + "/return");
            return new URI(requestToken.getAuthorizationURL());
        } catch (TwitterException | URISyntaxException e) {
            e.printStackTrace();
            return new URI(LOGIN_FAILED);
        }
    }

    public void getAccessToken(String oauth_token, String oauth_verifier) {
        if(oauth_token.equals(requestToken.getToken())) {
            System.out.println("Tokens do not match");
        }
        try {
            AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, oauth_verifier);
            twitter.setOAuthAccessToken(accessToken);
            twitter.updateStatus("Got access token, debugging via social media");
            System.out.println("Received access token");
        } catch (TwitterException e) {
            if (401 == e.getStatusCode()) {
                System.out.println("Unable to get the access token.");
            }
            e.printStackTrace();
        }
    }

    public void search(String searchString) {
        Query query = new Query(searchString);
        try {
            QueryResult result = twitter.search(query);
            tweetList.addTweets(twitter.getHomeTimeline());
//            tweetList.addTweets(result.getTweets());
            System.out.println("TwitterService " + tweetList.getTweetNo());
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}
