package hello.model;

import org.springframework.stereotype.Component;
import twitter4j.Status;

import java.util.ArrayList;
import java.util.List;

@Component
public class TweetList {
    private int TWEET_LIMIT = 10;
    private List<Status> tweets = new ArrayList<>();
    public int filtered;

    public void addTweets(List<Status> newTweets) {
        tweets.clear();
        if (newTweets.size() > TWEET_LIMIT) {
            tweets.addAll(newTweets.subList(0, TWEET_LIMIT));
        } else {
            tweets.addAll(newTweets);
        }
    }

    public int getFilteredTweetNo() {
        return filtered;
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

    public void setLimit(int limit) {
        this.TWEET_LIMIT = limit;
    }
}
