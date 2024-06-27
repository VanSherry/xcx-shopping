package com.example.xcxpojo.vo;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 查询到的单条购物车
 */
@Data
public class CartVO {
    private String id;
    private String goodsName;
    private Integer isSelected;
    private Integer quantity;
    private BigDecimal price;
    private String url;
}
