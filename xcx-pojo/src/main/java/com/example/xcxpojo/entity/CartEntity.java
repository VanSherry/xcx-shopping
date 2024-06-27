package com.example.xcxpojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("cart")
public class CartEntity {
    @TableId("id")
    private Long id;
    @TableField("user_id")
    private Long userId;
    @TableField("goods_id")
    private Long goodsId;
    @TableField("is_selected")
    private Integer isSelected;
    @TableField("status")
    private Integer status;
    @TableField("quantity")
    private Integer quantity;
}
