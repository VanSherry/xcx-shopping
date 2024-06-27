package com.example.xcxgood.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.xcxpojo.entity.GoodsClickEntity;

public interface GoodsClickService extends IService<GoodsClickEntity> {
    void insert(Long goodId);
}
