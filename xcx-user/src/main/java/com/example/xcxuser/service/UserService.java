package com.example.xcxuser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.xcxcommon.result.Result;
import com.example.xcxpojo.dto.LoginDTO;
import com.example.xcxpojo.dto.RegisterDTO;
import com.example.xcxpojo.entity.UserEntity;

import java.security.NoSuchAlgorithmException;

public interface UserService extends IService<UserEntity>{
    /**
     * 注册功能
     * @param registerDTO
     * @return
     */
    public Result<String> register(RegisterDTO registerDTO) throws Exception;

    /**
     * 登录功能
     * @param loginDTO
     * @return
     */
    Result<String> login(LoginDTO loginDTO) throws NoSuchAlgorithmException;
}
