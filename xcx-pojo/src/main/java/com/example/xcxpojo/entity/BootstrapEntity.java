package com.example.xcxpojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("bootstrap")
public class BootstrapEntity {
    @TableId("id")
    Long id;
    @TableField("url")
    String url;
    @TableField("good_id")
    Long GoodId;
}
