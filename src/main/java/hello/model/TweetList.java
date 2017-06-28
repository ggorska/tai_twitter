package hello.model;

import org.springframework.stereotype.Component;
import twitter4j.Status;

import java.util.ArrayList;
import java.util.List;

@Component
public class TweetList {
    private static final int MAX_TWEETS_NUMBER = 10;
    List<Status> tweets = new ArrayList<>();

    public void addTweets(List<Status> newTweets) {
        tweets.clear();
        tweets.addAll(newTweets.subList(0, MAX_TWEETS_NUMBER));
    }

    public int getTweetNo() {
        return tweets.size();
    }

    public String toString() {
        StringBuilder res = new StringBuilder("Tweets: ");
        for (Status s : tweets) {
            res.append(s.getText() + " ");
        }
        return res.toString();
    }

    public List<Status> getList() {
        return tweets;
    }
}
