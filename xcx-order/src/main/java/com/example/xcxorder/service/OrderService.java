package com.example.xcxorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.xcxpojo.entity.OrderEntity;
import com.example.xcxpojo.vo.OrderVO;

import java.util.ArrayList;

public interface OrderService extends IService<OrderEntity> {
    void insert();

    ArrayList<OrderVO> select();

    void pay(Long orderId) throws Exception;
}
