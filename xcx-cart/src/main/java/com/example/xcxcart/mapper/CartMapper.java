package com.example.xcxcart.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.xcxpojo.entity.CartEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CartMapper extends BaseMapper<CartEntity> {
}
