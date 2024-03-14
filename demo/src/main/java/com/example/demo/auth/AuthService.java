package com.example.demo.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.demo.security.JwtProvider;
import com.example.demo.system.MyResponse;
import com.example.demo.user.UserService;
import com.example.demo.user.dto.MyUserPrincipal;
import com.example.demo.user.dto.ResponseUserDto;
import com.example.demo.user.dto.UserDto;
import com.example.demo.user.entity.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthService {

    private final JwtProvider jwtProvider;

    private final UserService userService;

    public AuthService(
            JwtProvider jwtProvider,
            UserService userService) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    public MyResponse register(UserDto userDto) {
        User user = this.userService.findOne(userDto.username());

        if (user != null)
            return new MyResponse(false, "Username has taken", HttpStatus.CONFLICT.value());

        this.userService.create(userDto);
        return new MyResponse(true, "Register ok", HttpStatus.OK.value());
    }

    public Map<String, Object> handleLogin(Authentication authentication) {
        System.out.println(">>> handle login");

        MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
        ResponseUserDto userDto = new ResponseUserDto(principal.getUsername(), principal.getUser().getRole());

        // generate and update user refresh token
        String refreshToken = this.jwtProvider.generateToken(authentication, 30);
        System.out.println(">>> check refresh token: " + refreshToken);
        this.userService.updateRefreshToken(refreshToken, principal.getUsername());


        // generate token, useInfo and make response
        String token = this.jwtProvider.generateToken(authentication, 30);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userInfo", userDto);
        resultMap.put("token", token);

        return resultMap;
    }

    public void refreshToken(
            HttpServletRequest req, HttpServletResponse res) {
        final String header = req.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;

        if (header == null || !header.startsWith("Bearer "))
            throw new AccessDeniedException("");

        refreshToken = header.substring(7);
    }

}
