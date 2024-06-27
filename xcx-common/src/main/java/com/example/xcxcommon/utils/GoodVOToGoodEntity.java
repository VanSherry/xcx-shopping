package com.example.xcxcommon.utils;

import com.example.xcxpojo.entity.GoodsEntity;
import com.example.xcxpojo.vo.GoodByIdVO;

public class GoodVOToGoodEntity {
    public static GoodsEntity exchange(GoodByIdVO goodByIdVO){
        GoodsEntity goodsEntity = new GoodsEntity();

        goodsEntity.setName(goodByIdVO.getName());
        goodsEntity.setPrice(goodByIdVO.getPrice());
        goodsEntity.setUrl(goodByIdVO.getUrl());
        goodsEntity.setState(goodByIdVO.getState());
        goodsEntity.setDescription(goodByIdVO.getDescription());
        goodsEntity.setId(Long.valueOf(goodByIdVO.getId()));
        goodsEntity.setUpdateTime(goodByIdVO.getUpdateTime());
        goodsEntity.setUpTime(goodByIdVO.getUpTime());
        return goodsEntity;
    }
}
