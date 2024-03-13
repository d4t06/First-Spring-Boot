package com.example.demo.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.system.MyResponse;
import com.example.demo.user.UserService;
import com.example.demo.user.dto.MyUserPrincipal;
import com.example.demo.user.dto.UserDto;
import com.example.demo.user.entity.User;

@Service
public class AuthService {

    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public User login(UserDto userDto) {
        User foundedUser = this.userService.findOne(userDto.username());

        if (foundedUser == null)
            throw new UsernameNotFoundException("");

        if (foundedUser.getPassword() != userDto.password())
            throw new BadCredentialsException("");

        return foundedUser;
    }

    public MyResponse register(UserDto userDto) {
        User user = this.userService.findOne(userDto.username());

        if (user != null)
            return new MyResponse(false, "Username has taken", HttpStatus.CONFLICT.value());

        this.userService.create(userDto);
        return new MyResponse(true, "Register ok", HttpStatus.OK.value());
    }

    public Map<String, Object> createLoginInfo(Authentication authentication) {
        MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();

        
        UserDto userDto = new UserDto(principal.getUser().getId(), principal.getUsername(), null, principal.getUser().getRole());
        String token = "";
        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("userInfo", userDto);
        resultMap.put("token", token);

        return resultMap;
    }

}
