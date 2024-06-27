package com.example.xcxpojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@TableName(value = "goods")
public class GoodsEntity {
    @TableId("id")
    private Long id;
    @TableField("name")
    private String name;
    @TableField("description")
    private String description;
    @TableField("price")
    private BigDecimal price;
    @TableField("state")
    private Integer state;
    @TableField("url")
    private String url;
    @TableField("up_time")
    private LocalDateTime upTime;
    @TableField("update_time")
    private LocalDateTime updateTime;
}
