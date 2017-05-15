package hello.controller;

import hello.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class AuthController {

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public User getCurrentUser() {
        User user = new User();
        user.setUsername("ktos");

        return user;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login() {

    }
}
