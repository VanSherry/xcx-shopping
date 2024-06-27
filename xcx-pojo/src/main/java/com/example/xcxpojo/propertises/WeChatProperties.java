package com.example.xcxpojo.propertises;

import lombok.Data;
import org.springframework.stereotype.Component;
@Data
public class WeChatProperties {
    private String appid = null;
    private String mchid = null;
    private String notifyUrl = null;
    private String privateKeyFilePath = null;
    private String weChatPayCertFilePath = null;
    private String MchSerialNo = null;
}
