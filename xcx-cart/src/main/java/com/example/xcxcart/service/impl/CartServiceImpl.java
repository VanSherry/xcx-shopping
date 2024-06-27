package com.example.xcxcart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.xcxcart.mapper.CartMapper;
import com.example.xcxcart.service.CartService;
import com.example.xcxclient.client.GoodFeignClient;
import com.example.xcxcommon.constant.StateConstant;
import com.example.xcxcommon.context.UserContext;
import com.example.xcxcommon.utils.GoodVOToGoodEntity;
import com.example.xcxpojo.entity.CartEntity;
import com.example.xcxpojo.entity.GoodsEntity;
import com.example.xcxpojo.vo.CartVO;
import com.example.xcxpojo.vo.CartsVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CartServiceImpl extends ServiceImpl<CartMapper, CartEntity> implements CartService {
    private final CartMapper cartMapper;
    private final GoodFeignClient goodFeignClient;
    /**
     * 购物车中添加商品
     * @param goodsEntity
     */
    public void addGoodInCart(GoodsEntity goodsEntity) {
        if(lambdaQuery()
                .eq(CartEntity::getGoodsId,goodsEntity.getId())
                .eq(CartEntity::getUserId,UserContext.getUser())
                .exists())
        {
            Long cartId = lambdaQuery()
                    .eq(CartEntity::getGoodsId,goodsEntity.getId())
                    .eq(CartEntity::getUserId,UserContext.getUser())
                    .one()
                    .getId();
            QueryWrapper<CartEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",cartId);
            CartEntity cart = cartMapper.selectById(cartId);
            cart.setQuantity(cart.getQuantity()+1);
            cartMapper.update(cart,queryWrapper);
        }else{
            CartEntity cartEntity = new CartEntity();
            cartEntity.setGoodsId(goodsEntity.getId());
            cartEntity.setStatus(StateConstant.ONSALE);
            cartEntity.setQuantity(StateConstant.ONSALE);
            // 用户id通过微服务间传递
            cartEntity.setUserId(UserContext.getUser());
            cartEntity.setIsSelected(StateConstant.ONSALE);
            log.info(cartEntity.toString());
            cartMapper.insert(cartEntity);
        }
    }

    /**
     * 展示购物车
     * @return
     */
    public CartsVO selectCarts() {
        QueryWrapper<CartEntity> queryWrapper = new QueryWrapper<>();
        //
        queryWrapper.eq("user_id",UserContext.getUser())
                .eq("status",StateConstant.ONSALE);
        List<CartEntity> carts = cartMapper.selectList(queryWrapper);
        log.info(carts.toString());
        ArrayList<CartVO> cartVOS = new ArrayList<>();
        double sumPrice = 0.0;

        for (CartEntity cart : carts) {
            CartVO cartVO = new CartVO();
            GoodsEntity goodsEntity = GoodVOToGoodEntity.exchange(goodFeignClient.selectGoodsById(cart.getGoodsId()));
            log.info(goodsEntity.toString());

            cartVO.setId(String.valueOf(cart.getId()));
            cartVO.setIsSelected(cart.getIsSelected());
            cartVO.setQuantity(cart.getQuantity());
            cartVO.setGoodsName(goodsEntity.getName());
            cartVO.setUrl(goodsEntity.getUrl());
            cartVO.setPrice(BigDecimal.
                    valueOf(goodsEntity
                            .getPrice()
                            .toBigInteger()
                            .doubleValue() * cart.getQuantity()));

            if(cartVO.getIsSelected() == 1){
                sumPrice += cartVO.getPrice().doubleValue();
            }
            cartVOS.add(cartVO);
        }

        CartsVO cartsVO = new CartsVO();
        cartsVO.setCarts(cartVOS);
        cartsVO.setPrice(BigDecimal.valueOf(sumPrice));
        log.info(cartsVO.toString());
        return cartsVO;
    }

    /**
     * 增加购物车商品数量
     * @param cartId
     */
    public void addCartQuantity(Long cartId) {
        QueryWrapper<CartEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",cartId);
        CartEntity cart = cartMapper.selectById(cartId);
        cart.setQuantity(cart.getQuantity()+1);
        cartMapper.update(cart,queryWrapper);
    }

    /**
     * 减少购物车商品数量
     * @param cartId
     */
    public void decreaseCartQuantity(Long cartId) {
        QueryWrapper<CartEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",cartId);
        CartEntity cart = cartMapper.selectById(cartId);
        if(cart.getQuantity() > 1){
            cart.setQuantity(cart.getQuantity()-1);
            cartMapper.update(cart,queryWrapper);
        }else {
            cartMapper.deleteById(cartId);
        }

    }

    /**
     * 删除购物车商品数量
     * @param cartId
     */
    public void deleteCartQuantity(Long cartId) {
        log.info(cartId.toString());
        cartMapper.deleteById(cartId);
    }

    /**
     * 改变选中状态
     * @param cartId
     */
    public void changeSelected(Long cartId) {
        CartEntity cart = lambdaQuery().eq(CartEntity::getId, cartId).one();
        Integer isSelected = cart.getIsSelected();
        if (isSelected == 1){
            cart.setIsSelected(0);
            cartMapper.updateById(cart);
        }else {
            cart.setIsSelected(1);
            cartMapper.updateById(cart);
        }

    }

    /**
     * 根据是否选中查询购物车
     * @return
     */
    public CartsVO selectCartByIsSelect() {
        QueryWrapper<CartEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",UserContext.getUser())
                .eq("status",StateConstant.ONSALE)
                .eq("is_selected",StateConstant.ONSALE);
        List<CartEntity> carts = cartMapper.selectList(queryWrapper);
        log.info(carts.toString());
        ArrayList<CartVO> cartVOS = new ArrayList<>();
        double sumPrice = 0.0;

        for (CartEntity cart : carts) {
            CartVO cartVO = new CartVO();
            GoodsEntity goodsEntity = GoodVOToGoodEntity.exchange(goodFeignClient.selectGoodsById(cart.getGoodsId()));
            log.info(goodsEntity.toString());

            cartVO.setId(String.valueOf(cart.getId()));
            cartVO.setIsSelected(cart.getIsSelected());
            cartVO.setQuantity(cart.getQuantity());
            cartVO.setGoodsName(goodsEntity.getName());
            cartVO.setUrl(goodsEntity.getUrl());
            cartVO.setPrice(BigDecimal.
                    valueOf(goodsEntity
                            .getPrice()
                            .toBigInteger()
                            .doubleValue() * cart.getQuantity()));

            if(cartVO.getIsSelected() == 1){
                sumPrice += cartVO.getPrice().doubleValue();
            }
            cartVOS.add(cartVO);
        }

        CartsVO cartsVO = new CartsVO();
        cartsVO.setCarts(cartVOS);
        cartsVO.setPrice(BigDecimal.valueOf(sumPrice));
        log.info(cartsVO.toString());
        return cartsVO;
    }
}
