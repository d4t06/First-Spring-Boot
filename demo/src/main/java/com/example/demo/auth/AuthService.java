package com.example.demo.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.demo.security.JwtProvider;
import com.example.demo.system.MyResponse;
import com.example.demo.user.UserService;
import com.example.demo.user.dto.MyUserPrincipal;
import com.example.demo.user.dto.ResponseUserDto;
import com.example.demo.user.dto.UserDto;
import com.example.demo.user.entity.User;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthService {

    private final JwtProvider jwtProvider;

    private final UserService userService;

    public static int REFRESH_TOKEN_EXPIRE = 60 * 60 * 24;
    public static int ACCESS_TOKEN_EXPIRE = 30;

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

    public Map<String, Object> handleLogin(
            Authentication authentication,
            HttpServletResponse res) {

        System.out.println(">>> run handle login");

        MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
        ResponseUserDto userDto = new ResponseUserDto(principal.getUsername(), principal.getUser().getRole());

        // generate and update user refresh token
        String refreshToken = this.jwtProvider.generateRefreshToken(principal.getUsername(), REFRESH_TOKEN_EXPIRE);
        // create httpOnly cookie
        Cookie cookie = new Cookie("jwt", refreshToken);
        cookie.setHttpOnly(true); // must enable to make sure that token can not be access
        cookie.setMaxAge(24 * 60 * 60 * 1000); // one day
        // store refresh token to client browser cookie
        res.addCookie(cookie);  

        // get user role as string for generate access token
        String authorities = this.jwtProvider.getJoinAuthoritiesFromPrefix(authentication.getAuthorities());
        // generate token, useInfo and make response
        String token = this.jwtProvider.generateToken(authorities, principal.getUsername(), ACCESS_TOKEN_EXPIRE);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userInfo", userDto);
        resultMap.put("token", token);

        return resultMap;
    }

    public Map<String, Object>  refreshToken(
            String refreshToken) {

        String username = this.jwtProvider.parserUsernameFromToken(refreshToken);
        User user = this.userService.findOne(username);

        // get user role as string for generate access token
        String authorities = this.jwtProvider.getJoinAuthoritiesFromUserRole(user.getRole());
        // generate token, useInfo and make response
        String token = this.jwtProvider.generateToken(authorities, user.getUsername(), ACCESS_TOKEN_EXPIRE);

        ResponseUserDto userDto = new ResponseUserDto(user.getUsername(), user.getRole());
        // make response data
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userInfo", userDto);
        resultMap.put("token", token);

        return resultMap;
    }

}
