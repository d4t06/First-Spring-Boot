package com.example.demo.user.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.user.dto.UserDto;
import com.example.demo.user.entity.User;


@Component
public class UserDtoToUser implements Converter<UserDto, User> {

    @Override
    public User convert(UserDto source) {
        User user = new User();
        user.setUsername(source.username());
        user.setPassword(source.password());
        user.setRole("USER");

        return user;
    }

}
