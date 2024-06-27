package com.example.xcxclient.client;

import com.example.xcxpojo.entity.GoodsEntity;
import com.example.xcxpojo.vo.GoodByIdVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(value = "xcx-good")
public interface GoodFeignClient {
    /**
     * 根据商品id查询商品信息
     * @return
     */
    @GetMapping("goods/selectGoodsById")
    GoodByIdVO selectGoodsById(@RequestParam("id") Long id);
}
