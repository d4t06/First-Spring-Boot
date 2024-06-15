package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.system.MyResponse;

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    @GetMapping("")
    public MyResponse greet() {
        return new MyResponse(true, "hello world", 200);
    }
}
