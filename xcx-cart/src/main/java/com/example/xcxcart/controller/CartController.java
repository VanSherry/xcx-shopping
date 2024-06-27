package com.example.xcxcart.controller;

import com.example.xcxcart.service.CartService;
import com.example.xcxclient.client.GoodFeignClient;
import com.example.xcxcommon.result.Result;
import com.example.xcxcommon.utils.GoodVOToGoodEntity;
import com.example.xcxpojo.entity.GoodsEntity;
import com.example.xcxpojo.vo.CartsVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CartController {
    private final CartService cartService;
    private final GoodFeignClient goodFeignClient;

    /**
     *根据商品id将商品添加到购物车
     * @param id
     */
    @PutMapping()
    public Result<String> addGoodInCart(Long id){
        GoodsEntity goodsEntity = GoodVOToGoodEntity.exchange(goodFeignClient.selectGoodsById(id));
        log.info(goodsEntity.toString());
        cartService.addGoodInCart(goodsEntity);
        return Result.success("加入购物车成功");
    }

    /**
     * 展示购物车
     * @return
     */
    @GetMapping()
    public Result<CartsVO> selectCarts(){
        return Result.success(cartService.selectCarts());
    }

    /**
     * 增加购物车中商品数量
     * @param cartId
     * @return
     */
    @PutMapping("/add")
    public Result<String> addCartQuantity(Long cartId){
        cartService.addCartQuantity(cartId);
        return Result.success("增加成功");
    }

    /**
     * 减少购物车中商品数量
     * @param cartId
     * @return
     */
    @PutMapping("/decrease")
    public Result<String> decreaseCartQuantity(Long cartId){
        cartService.decreaseCartQuantity(cartId);
        return Result.success("减少成功");
    }

    /**
     * 删除购物车中商品数量
     * @param cartId
     * @return
     */
    @PutMapping("/delete")
    public Result<String> deleteCartQuantity(Long cartId){
        cartService.deleteCartQuantity(cartId);
        return Result.success("删除成功");
    }

    /**
     * 改变选中状态
     * @param cartId
     * @return
     */
    @PutMapping("/changeSelected")
    public Result<String> changeSelected(Long cartId){
        cartService.changeSelected(cartId);
        return Result.success("改变成功");
    }

    /**
     *根据是否选中查询购物车
     */
    @GetMapping("/selectCartByIsSelect")
    public CartsVO selectCartByIsSelect(){
        CartsVO cartsVO = cartService.selectCartByIsSelect();
        return cartsVO;
    }
}
