package com.example.paymentdemo.utils;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class HttpUtils {

    private static final Gson gson = new Gson();

    /**
     * encapsulate get
     * @param url
     * @return
     */
    public static Map<String, Object> doGet(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        Map<String, Object> map = new HashMap<>();

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000) // connect timeout
                .setConnectionRequestTimeout(5000)  // request timeout
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)  // allow auto redirect
                .build();

        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);

        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                String jsonResult = EntityUtils.toString(httpResponse.getEntity());
                map = gson.fromJson(jsonResult, map.getClass());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * encapsulate post
     * @return
     */
    public static String doPost(String url, String data,int timeout){
        CloseableHttpClient httpClient =  HttpClients.createDefault();

        //timeout setting
        RequestConfig requestConfig =  RequestConfig.custom().setConnectTimeout(timeout) // connect timeout
                .setConnectionRequestTimeout(timeout)// request timeout
                .setSocketTimeout(timeout)
                .setRedirectsEnabled(true)  //allow redirect
                .build();


        HttpPost httpPost  = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        httpPost.addHeader("Content-Type","text/html; chartset=UTF-8");

        if(data != null && data instanceof  String){ //use string as params
            StringEntity stringEntity = new StringEntity(data,"UTF-8");
            httpPost.setEntity(stringEntity);
        }

        try{

            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();

            if(httpResponse.getStatusLine().getStatusCode() == 200){
                String result = EntityUtils.toString(httpEntity);
                return result;
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                httpClient.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return null;

    }
}
