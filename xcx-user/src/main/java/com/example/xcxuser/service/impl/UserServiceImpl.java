package com.example.xcxuser.service.impl;

import com.alibaba.nacos.common.utils.MD5Utils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.xcxcommon.result.Result;
import com.example.xcxcommon.utils.JWTUtils;
import com.example.xcxpojo.dto.LoginDTO;
import com.example.xcxpojo.dto.RegisterDTO;
import com.example.xcxpojo.entity.UserEntity;
import com.example.xcxuser.mapper.UserMapper;
import com.example.xcxuser.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService{
    private final String regexPhone = "1[3456789]\\d{9}";
    private final String regexPassword = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    private final UserMapper userMapper;
    /**
     * 注册功能
     * @param registerDTO
     * @return
     */
    public Result<String> register(RegisterDTO registerDTO) throws Exception{

        //正则表达式
        Pattern patternPhone = Pattern.compile(regexPhone);
        Matcher matcherPhone = patternPhone.matcher(registerDTO.getPhoneNum());

        Pattern patternPass = Pattern.compile(regexPassword);
        Matcher matcherPass = patternPass.matcher(registerDTO.getPassword());
        //判断是否符合
        if (!matcherPhone.matches()){
            return Result.error("手机号格式错误");
        }
        if (lambdaQuery().eq(UserEntity::getPhoneNum,registerDTO.getPhoneNum()).exists()){
            return Result.error("该手机号已注册");
        }
        if (!matcherPass.matches()){
            return Result.error("密码必须包含字母和数字，并且长度不小于8");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setPhoneNum(registerDTO.getPhoneNum());
        userEntity.setPassword(MD5Utils.md5Hex(registerDTO.getPassword().getBytes()));
        userMapper.insert(userEntity);
        return Result.success("注册成功");
    }

    /**
     * 登录功能
     * @param loginDTO
     * @return
     */
    public Result<String> login(LoginDTO loginDTO) throws NoSuchAlgorithmException {
        //判断手机号密码
        if(!lambdaQuery().eq(UserEntity::getPhoneNum,loginDTO.getPhoneNum())
                .eq(UserEntity::getPassword,MD5Utils.md5Hex(loginDTO.getPassword().getBytes()))
                .exists())
        {
            return Result.error("账号密码错误");
        }
        //生成jwt
        Map<String, Object> map = new HashMap<>();
        map.put("用户id", lambdaQuery().eq(UserEntity::getPhoneNum,loginDTO.getPhoneNum())
                .eq(UserEntity::getPassword,MD5Utils.md5Hex(loginDTO.getPassword().getBytes()))
                .one()
                .getId());
        String jwt = JWTUtils.createJWT(map);
        return Result.success(jwt);
    }
}
