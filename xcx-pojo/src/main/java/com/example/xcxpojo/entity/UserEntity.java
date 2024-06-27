package com.example.xcxpojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("users")
public class UserEntity {
    @TableId("id")
    private Long id;
    @TableField("phone_num")
    private String phoneNum;
    @TableField("password")
    private String password;
    @TableField("name")
    private String name;
}
