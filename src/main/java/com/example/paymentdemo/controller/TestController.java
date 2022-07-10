package com.example.paymentdemo.controller;

import com.example.paymentdemo.config.WeChatConfig;
import com.example.paymentdemo.mapper.VideoMapper;
import com.example.paymentdemo.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private WeChatConfig weChatConfig;

    @RequestMapping("test_config")
    public JsonData testConfig() {
        System.out.println("hello");
        return JsonData.buildSuccess("hello, id:" + weChatConfig.getAppId());
    }

    @Autowired
    private VideoMapper videoMapper;

    @RequestMapping("test_db")
    public Object testDB() {
        return videoMapper.findAll();
    }
}
