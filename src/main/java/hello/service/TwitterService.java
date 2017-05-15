package hello.service;

import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import java.net.URI;
import java.net.URISyntaxException;

//@Slf4j - logger
@Service
public class TwitterService {
    public static final String APP_URI = "https://taiproject2017.herokuapp.com";
    public static final String LOGIN_FAILED = APP_URI + "/loginfailed";
    private RequestToken requestToken;
    private Twitter twitter;
    private AccessToken accessToken;

    TwitterService() {
        ConfigurationBuilder conf = new ConfigurationBuilder();
        conf.setDebugEnabled(true)
                .setOAuthConsumerKey("NuiqWe8qGEBUeYLKoswgzOqVm")
                .setOAuthConsumerSecret("mSZUhCoPCNwfv3yK6HkiXtmvVZlPvT9pnxEpzo8J02mnDajcPt");
        twitter = new TwitterFactory(conf.build()).getInstance();
    }

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
        try {
            accessToken = twitter.getOAuthAccessToken(oauth_token, oauth_verifier);
            twitter.setOAuthAccessToken(accessToken);
            twitter.updateStatus("Got access token, debugging via social media");
        } catch (TwitterException e) {
            if (401 == e.getStatusCode()) {
                System.out.println("Unable to get the access token.");
            } else {
                e.printStackTrace();
            }
        }
    }
}
