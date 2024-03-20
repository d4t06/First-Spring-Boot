package com.example.demo.auth;

import java.util.Map;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.system.MyResponse;
import com.example.demo.user.dto.UserDto;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

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
            @RequestBody UserDto userDto,
            HttpServletResponse res) {
        System.out.println(">>> login route handler");

        // this method call loadUserByUsername methods
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userDto.username(), userDto.password()));

        System.out.println(">>> run set authentication");

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Map<String, Object> userRes = this.authService.handleLogin(authentication, res);

        return new MyResponse(true, "Login successful" + authentication.getName(), 200, userRes);
    }

    @PostMapping("/register")
    public MyResponse signUp(
            @RequestBody UserDto userDto) {

        return this.authService.register(userDto);
    }

    @GetMapping("/refresh")
    public MyResponse refreshToken(@CookieValue("jwt") String refreshToken) {

        if (refreshToken == null)
            throw new AccessDeniedException("");

        System.out.println(">>> refresh toeken");
        Map<String, Object> resultMap = this.authService.refreshToken(refreshToken);

        return new MyResponse(true, "Refresh token successful", 200, resultMap);
    }

    @GetMapping("/logout")
    public MyResponse logOut(
            HttpServletResponse res) {

        // create null cookie
        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true); // must enable to make sure that token can not be access
        cookie.setMaxAge(0);
        // replace refresh token in client browser cookie
        res.addCookie(cookie);

        return new MyResponse(true, "Logout successful", 200);

    }
}
