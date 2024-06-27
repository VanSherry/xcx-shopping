package com.example.xcxpojo.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CategoryVO {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private String url;
}
