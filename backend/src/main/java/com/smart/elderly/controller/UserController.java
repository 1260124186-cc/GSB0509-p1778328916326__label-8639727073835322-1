package com.smart.elderly.controller;

import com.smart.elderly.common.Result;
import com.smart.elderly.entity.User;
import com.smart.elderly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<User> login(@RequestBody User user) {
        User dbUser = userService.login(user.getUsername(), user.getPassword());
        if (dbUser != null) {
            dbUser.setPassword(null); // 安全性：不返回密码
            return Result.success(dbUser);
        }
        return Result.error("用户名或密码错误");
    }

    @PostMapping("/register")
    public Result<String> register(@RequestBody User user) {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            return Result.error("用户名不能为空");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return Result.error("密码不能为空");
        }
        // 检查用户名是否已存在
        User existingUser = userService
                .getOne(new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<User>()
                        .eq(User::getUsername, user.getUsername()));
        if (existingUser != null) {
            return Result.error("该用户名已被注册");
        }
        userService.register(user);
        return Result.success("注册成功");
    }
}
