package com.example.xcxpojo.vo;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
public class OrderVO {
    private String id;
    private String goodName;
    private LocalDateTime createTime;
    private Integer status;
    private BigDecimal price;
}
