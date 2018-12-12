package com.and.sms.controller;

import com.and.sms.service.ComputationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class InterestController {
    @Autowired
    private ComputationService service;

    @RequestMapping(value = "/user")
    public String findUserPairListWithMaxLength(@RequestBody Set<String> userNames) {
        System.out.println("start...");
        long startTime = System.currentTimeMillis();
        String result = service.findUserPairListWithMaxLength(userNames).toString();
        System.out.println("result time is " + (System.currentTimeMillis() - startTime));
        System.out.println("finish...");
        return result;
    }
}
