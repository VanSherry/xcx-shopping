package com.example.xcxpojo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * 返回给前端展示的购物车
 */
@Data
public class CartsVO {
    private ArrayList<CartVO> carts;
    private BigDecimal price;
}
