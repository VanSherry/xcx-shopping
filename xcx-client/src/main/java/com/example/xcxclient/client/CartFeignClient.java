package com.example.xcxclient.client;

import com.example.xcxcommon.result.Result;
import com.example.xcxpojo.vo.CartsVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(value = "xcx-cart")
public interface CartFeignClient {
    /**
     *根据是否选中查询购物车
     */
    @GetMapping("cart/selectCartByIsSelect")
    CartsVO selectCartByIsSelect();

    /**
     * 根据id删除购物车
     * @param cartId
     * @return
     */
    @PutMapping("cart/delete")
    Result<String> deleteCartQuantity(@RequestParam("cartId") Long cartId);
}
