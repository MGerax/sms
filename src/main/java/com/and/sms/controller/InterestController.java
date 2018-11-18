package com.and.sms.controller;

import com.and.sms.model.User;
import com.and.sms.service.ComputationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InterestController {
    @Autowired
    private ComputationService service;

    @RequestMapping("/test")
    public String test() {
        return "test method";
    }

    @RequestMapping(value = "/user")
    public String findUserPairListWithMaxLength(@RequestBody List<User> users) {
        return service.findUserPairListWithMaxLength(users).toString();
    }
}
