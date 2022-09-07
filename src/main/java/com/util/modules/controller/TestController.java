package com.util.modules.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class TestController {

    @PostMapping("/test")
    @ResponseBody
    public String test(@RequestBody HashMap<String, String> map) {
        System.out.println(map);
        return "test";
    }

}
