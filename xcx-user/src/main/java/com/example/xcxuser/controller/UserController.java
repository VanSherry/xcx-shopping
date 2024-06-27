package com.example.xcxuser.controller;

import com.example.xcxcommon.result.Result;
import com.example.xcxpojo.dto.LoginDTO;
import com.example.xcxpojo.dto.RegisterDTO;
import com.example.xcxuser.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;


@RestController
@ApiOperation("用户管理接口")
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public Result<String> register(@RequestBody RegisterDTO registerDTO) throws Exception {

        return userService.register(registerDTO);
    }
    @PostMapping("/login")
    public Result<String> register(@RequestBody LoginDTO loginDTO) throws NoSuchAlgorithmException {
        return userService.login(loginDTO);
    }
}
