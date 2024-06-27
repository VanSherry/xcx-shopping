package com.example.xcxpojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("good_category")
public class CategoryEntity {
    @TableId("id")
    private Long id;
    @TableField("good_id")
    private Long goodId;
    @TableField("category")
    private Integer category;
}
