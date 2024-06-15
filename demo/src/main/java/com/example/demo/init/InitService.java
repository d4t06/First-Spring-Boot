package com.example.demo.init;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.category.CategoryService;
import com.example.demo.category.dto.CategoryDto;
import com.example.demo.system.MyResponse;
import com.example.demo.user.UserService;
import com.example.demo.user.dto.UserDto;
import com.example.demo.user.entity.User;

@Service
public class InitService {

    private final UserService userService;
    private final CategoryService categoryService;

    public InitService(UserService userService,
            CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    public MyResponse init(UserDto userDto) {

        User user = this.userService.findOne(userDto.username());
        if (user != null)
            return new MyResponse(false, "Username has taken", HttpStatus.CONFLICT.value());

        User adminUser = this.userService.init(userDto);
        CategoryDto home = new CategoryDto(null, "home", "home", "", 0, null, null, null, null);

        this.categoryService.create(home);

        return new MyResponse(true, "Init successfull", 200, adminUser);
    }

}
