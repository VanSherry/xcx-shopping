package com.example.xcxpojo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class GoodByIdVO {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer state;
    private String url;
    private LocalDateTime upTime;
    private LocalDateTime updateTime;
}
