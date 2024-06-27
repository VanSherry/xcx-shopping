package com.example.xcxorder.controller;

import com.example.xcxcommon.result.Result;
import com.example.xcxorder.service.OrderService;
import com.example.xcxpojo.vo.OrderVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/order")
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/insert")
    public Result<String> createOrder(){
        orderService.insert();
        return  Result.success("创建订单成功");
    }
    @GetMapping("/select")
    public Result<ArrayList<OrderVO>> selectOrder(){
        return Result.success(orderService.select());
    }
    @PostMapping("/pay")
    public Result<String> pay(Long orderId) throws Exception {
        orderService.pay(orderId);
        return Result.success("支付成功");
    }
}
