package hello.model;

import org.springframework.stereotype.Component;
import twitter4j.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Limonka on 2017-05-22.
 */
@Component
public class TweetList {
    List<Status> tweets = new ArrayList<>();

    public void addTweets(List<Status> newTweets) {
        tweets.clear();
        tweets.addAll(newTweets);
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
