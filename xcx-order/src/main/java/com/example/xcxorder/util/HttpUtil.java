package com.example.xcxorder.util;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtil {

    static final int TIMEOUT_MSEC = 5 * 1000;

    /**
     * 配置http请求
     */
    private static RequestConfig requestConfig(){
        return RequestConfig.custom()
                .setConnectTimeout(TIMEOUT_MSEC)
                .setConnectionRequestTimeout(TIMEOUT_MSEC)
                .setSocketTimeout(TIMEOUT_MSEC).build();
    }
    /**
     * POST请求
     */
    public static String doPost(String url, Map<String,String> paramMap) throws IOException {
        //创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";

        try {
            //创建Http Post请求
            HttpPost httpPost = new HttpPost(url);

            //创建参数列表
            if (paramMap != null){
                List<NameValuePair> paramList = new ArrayList<>();
                for (Map.Entry<String,String> param : paramMap.entrySet()){
                    paramList.add(new BasicNameValuePair(param.getKey(),param.getValue()));
                }
                //模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }

            httpPost.setConfig(requestConfig());

            //执行http请求
            response = httpClient.execute(httpPost);

            resultString = EntityUtils.toString(response.getEntity(),"UTF-8");
        }catch (Exception e) {
            throw e;
        }finally {
            try{
                response.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        return resultString;
    }
}
