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
import java.util.ArrayList;
import java.util.List;


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

    public void filter(String searchString, int limit) {
        tweetList.setLimit(limit);
        try {
            List<Status> timeline  = twitter.getHomeTimeline();
            System.out.println("timeline " + timeline.size());
            tweetList.filtered = 0;
            List<Status> filtered = new ArrayList<>();
            int i = 0;
            while (filtered.size() < limit && i < timeline.size()) {
                if (!contains(timeline.get(i), searchString)) {
                    filtered.add(timeline.get(i));
                } else {
                    tweetList.filtered++;
                }
                i++;
            }
            tweetList.addTweets(filtered);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        System.out.println("TwitterService filtered out " + tweetList.getFilteredTweetNo());
    }

    private boolean contains(Status status, String searchString) {
        if(searchString.equals("")) {
            return false;
        }
        String[] words = status.getText().split(" ");
        for (String word : words) {
            if (word.startsWith(searchString)) {
                return true;
            }
        }
        return false;
    }


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

    public void search(String searchString, int limit) {
        Query query = new Query(searchString);
        try {
            QueryResult result = twitter.search(query);
            tweetList.addTweets(result.getTweets());
            System.out.println("TwitterService " + tweetList.getFilteredTweetNo());
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    public void setTwitter(Twitter twitter) {
        this.twitter = twitter;
    }
}
