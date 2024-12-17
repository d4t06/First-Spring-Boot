package com.example.demo.init;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.system.MyResponse;
import com.example.demo.user.dto.UserDto;

@RestController
@RequestMapping("/init")
public class InitController {

    private final InitService initService;

    public InitController(InitService initService) {
        this.initService = initService;
    }

    @PostMapping("")
    public MyResponse init(@RequestBody UserDto userDto) {

        return this.initService.init(userDto);
    }
}
