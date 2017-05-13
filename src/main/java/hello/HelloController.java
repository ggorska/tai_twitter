package hello;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.net.URI;
import java.net.URISyntaxException;

@Controller
public class HelloController {
    Status status = null;

    @RequestMapping("/")
    public String index() {
        return "index.html";
    }

    @RequestMapping("/new")
    public String content() {
        return "logged_in.html";
    }

    @RequestMapping("/twitter")
    public String post() {
        ConfigurationBuilder conf = new ConfigurationBuilder();
        conf.setDebugEnabled(true)
                .setOAuthConsumerKey("NuiqWe8qGEBUeYLKoswgzOqVm")
                .setOAuthConsumerSecret("mSZUhCoPCNwfv3yK6HkiXtmvVZlPvT9pnxEpzo8J02mnDajcPt")
                .setOAuthAccessToken("862343971678425090-hYXhndkWdoUHal54zI2ZT25hGpoEdjS")
                .setOAuthAccessTokenSecret("y9802EE0lYp5Sn5nTXOuGpFrzQwQjQGldqBvx1xxB17dw");

        Twitter twitter = new TwitterFactory(conf.build()).getInstance();
        try {
            //specify callback url
            //RequestToken requestToken = twitter.getOAuthRequestToken();
            status = twitter.updateStatus("Hello world!");
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        System.out.println("Successfully updated the status to [" + status.getText() + "].");
        return "Successfully updated the status to [" + status.getText() + "].";
    }

    @RequestMapping("/redirect")
    public ResponseEntity redirect() throws URISyntaxException {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(new URI("http://localhost:8080/new")).build();
    }
}