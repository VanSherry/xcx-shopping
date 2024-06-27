package com.example.xcxpojo.dto;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class UploadGoodsDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private String url;
}
