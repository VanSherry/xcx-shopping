package com.example.xcxorder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.xcxclient.client.CartFeignClient;
import com.example.xcxcommon.constant.StateConstant;
import com.example.xcxcommon.context.UserContext;
import com.example.xcxorder.mapper.OrderMapper;
import com.example.xcxorder.service.OrderService;
import com.example.xcxorder.util.WeChatPayUtil;
import com.example.xcxpojo.entity.CartEntity;
import com.example.xcxpojo.entity.OrderEntity;
import com.example.xcxpojo.propertises.WeChatProperties;
import com.example.xcxpojo.vo.CartVO;
import com.example.xcxpojo.vo.CartsVO;
import com.example.xcxpojo.vo.OrderVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderEntity> implements OrderService {
    private final CartFeignClient cartFeignClient;

    private final OrderMapper orderMapper;
    private final WeChatPayUtil weChatPayUtil;

    /**
     * 生成订单
     */
    public void insert() {
        CartsVO cartsVO = cartFeignClient.selectCartByIsSelect();
        OrderEntity orderEntity = new OrderEntity();
        ArrayList<CartVO> carts = cartsVO.getCarts();

        orderEntity.setPrice(cartsVO.getPrice());
        orderEntity.setStatus(StateConstant.ONSALE);
        orderEntity.setUserId(UserContext.getUser());
        orderEntity.setCreateTime(LocalDateTime.now());
        orderEntity.setUpdateTime(LocalDateTime.now());

        String goodsName = "";

        for (CartVO cart : carts) {
            goodsName += (cart.getGoodsName() + ",");
            log.info(cart.getId());
            cartFeignClient.deleteCartQuantity(Long.valueOf(cart.getId()));
        }
        orderEntity.setGoodsName(goodsName);

        orderMapper.insert(orderEntity);
        log.info(orderEntity.toString());
    }

    /**
     * 查询订单
     * @return
     */
    public ArrayList<OrderVO> select() {
        ArrayList<OrderEntity> list = (ArrayList<OrderEntity>) lambdaQuery()
                .eq(OrderEntity::getUserId, UserContext.getUser())
                .eq(OrderEntity::getStatus, StateConstant.ONSALE)
                .eq(OrderEntity::getStatus,StateConstant.PAYFAIL)
                .list();
        ArrayList<OrderVO> arrayList = new ArrayList<>();
        try {
            for (OrderEntity orderEntity : list) {
                OrderVO orderVO = new OrderVO();
                orderVO.setStatus(orderEntity.getStatus());
                orderVO.setId(String.valueOf(orderEntity.getId()));
                orderVO.setCreateTime(orderEntity.getCreateTime());
                orderVO.setGoodName(orderEntity.getGoodsName());
                orderVO.setPrice(orderEntity.getPrice());
                log.info(orderVO.toString());
                arrayList.add(orderVO);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.info(arrayList.toString());
        return arrayList;
    }

    /**
     * 根据id查询订单
     * @param orderId
     */
    public OrderEntity selectById(Long orderId){
        OrderEntity one = lambdaQuery().eq(OrderEntity::getId, orderId).one();
        return one;
    }

    /**
     * 微信支付
     * @param orderId
     */
    public void pay(Long orderId) {
        OrderEntity orderEntity = this.selectById(orderId);
        try {
            weChatPayUtil.pay(orderId.toString(),orderEntity.getPrice(),orderEntity.getGoodsName(),UserContext.getUser().toString());
            orderEntity.setStatus(StateConstant.PAYSUCCESS);
            orderMapper.updateById(orderEntity);
        }catch (Exception e){
            orderEntity.setStatus(StateConstant.PAYFAIL);
            orderMapper.updateById(orderEntity);
        }
    }
}
