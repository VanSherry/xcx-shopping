package com.example.xcxcart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.xcxpojo.entity.CartEntity;
import com.example.xcxpojo.entity.GoodsEntity;
import com.example.xcxpojo.vo.CartsVO;

public interface CartService extends IService<CartEntity> {
    void addGoodInCart(GoodsEntity goodsEntity);

    CartsVO selectCarts();

    void addCartQuantity(Long cartId);

    void decreaseCartQuantity(Long cartId);

    void deleteCartQuantity(Long cartId);

    void changeSelected(Long cartId);

    CartsVO selectCartByIsSelect();
}
