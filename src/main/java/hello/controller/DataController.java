package hello.controller;

import hello.model.TweetList;
import hello.model.User;
import hello.service.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api")
@RestController
public class DataController {
    @Autowired
    private TweetList tweetList;
    @Autowired
    private TwitterService service;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public User getCurrentUser() {
        User user = new User();
        user.setUsername(service.getUserName());
        return user;
    }

    @RequestMapping("/tweets")
    public List<String> tweetList() {
        List<String> ids = new ArrayList<>();
        for ( Status status : tweetList.getList()) {
            ids.add("" +status.getId());
        }
        return ids;
    }

    @RequestMapping("/tweetnum")
    public int tweetNum() {
        System.out.println("data controller getting tweet number " + tweetList.getTweetNo());
        return tweetList.getTweetNo();
    }
}
