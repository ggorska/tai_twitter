package hello.controller;

import hello.service.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URISyntaxException;

@Controller
public class HelloController {

    @Autowired
    private TwitterService twitterService;

    @RequestMapping("/")
    public String index() {
        return "index.html";
    }

    @RequestMapping("/search")
    public String searchResults(@RequestParam("search") String searchString) {
        twitterService.search(searchString);
        return "search_results.html";
    }

    @RequestMapping("/new")
    public String content() {
        return "logged_in.html";
    }

    @RequestMapping(TwitterService.LOGIN_FAILED)
    public String loginFailed() {
        return "failed login";
    }

    @RequestMapping("/login")
    public ResponseEntity login() throws URISyntaxException {

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(twitterService.getLoginUri()).build();
    }

    @RequestMapping(value = "/return")
    public String loggedIn(
            @RequestParam("oauth_token") String oauth_token,
            @RequestParam("oauth_verifier") String oauth_verifier){
        twitterService.getAccessToken(oauth_token, oauth_verifier);
        return "logged_in.html";
    }
}