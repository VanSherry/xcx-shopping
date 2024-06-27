package com.example.xcxgood.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.xcxgood.mapper.GoodsClickMapper;
import com.example.xcxgood.service.GoodsClickService;
import com.example.xcxpojo.entity.GoodsClickEntity;
import org.springframework.stereotype.Service;

@Service
public class GoodsClickServiceImpl extends ServiceImpl<GoodsClickMapper, GoodsClickEntity> implements GoodsClickService {
    @Override
    public void insert(Long goodId) {
        // TODO:跨模块来获得用户id进行实现
    }
}
