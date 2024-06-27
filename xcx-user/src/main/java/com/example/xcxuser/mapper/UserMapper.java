package com.example.xcxuser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.xcxpojo.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserEntity>{
}
