package com.example.xcxpojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("orders")
public class OrderEntity {
    @TableId("id")
    private Long id;
    @TableField("user_id")
    private Long userId;
    @TableField("goods_name")
    private String goodsName;
    @TableField("create_time")
    private LocalDateTime createTime;
    @TableField("update_time")
    private LocalDateTime updateTime;
    @TableField("status")
    private Integer status;
    @TableField("price")
    private BigDecimal price;
}
