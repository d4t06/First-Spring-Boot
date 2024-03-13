package com.example.demo.auth;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.system.MyResponse;
import com.example.demo.user.dto.UserDto;

@RestController()
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    private final AuthenticationManager authenticationManager;

    public AuthController(
            AuthenticationManager authenticationManager,
            AuthService authService) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public MyResponse signIn(
            @RequestBody UserDto userDto) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userDto.username(), userDto.password()));

        // after loadUserByUsername
        // set authentication for spring secure 
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Map<String, Object> userRes = this.authService.createLoginInfo(authentication);

        return new MyResponse(true, "Login successful" + authentication.getName(), 200, userRes);
    }

    @PostMapping("/register")
    public MyResponse signUp(
            @RequestBody UserDto userDto) {

        return this.authService.register(userDto);
    }

    @GetMapping("/refresh")
    public MyResponse signin() {

        return new MyResponse(false, "Refresh token route", null);

    }
}
