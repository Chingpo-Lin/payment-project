package com.example.paymentdemo.service.impl;

import com.example.paymentdemo.config.WeChatConfig;
import com.example.paymentdemo.domain.User;
import com.example.paymentdemo.mapper.UserMapper;
import com.example.paymentdemo.service.UserService;
import com.example.paymentdemo.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private WeChatConfig weChatConfig;

    @Autowired
    private UserMapper userMapper;

    @Override
    public User saveWeChatUser(String code) {

        String accessTokenUrl = String.format(WeChatConfig.getOpenAccessTokenUrl(),
                weChatConfig.getOpenAppid(), weChatConfig.getOpenAppsecret());

        // get accessToken
        Map<String, Object> baseMap = HttpUtils.doGet(accessTokenUrl);

        if (baseMap == null || baseMap.isEmpty()) return null;

        String accessToken = (String) baseMap.get("access_token");
        String openId = (String) baseMap.get("openid");

        User dbUser = userMapper.findByOpenid(openId);
        if (dbUser != null) {
            return dbUser;
        }

        // get user info
        String userInfoUrl = String.format(WeChatConfig.getOpenUserInfoUrl(), accessToken, openId);

        Map<String, Object> baseUser = HttpUtils.doGet(userInfoUrl);

        if (baseUser == null || baseUser.isEmpty()) return null;

        String nickname = (String) baseUser.get("nickname");
        Double sexTemp = (Double) baseUser.get("sex");
        int gender = sexTemp.intValue();
        String province = (String) baseUser.get("province");
        String city = (String) baseUser.get("city");
        String country = (String) baseUser.get("country");
        String headimgurl = (String) baseUser.get("headimgurl");
        StringBuilder stringBuilder = new StringBuilder(country).append("||").append(province).append("||").append(city);
        String address = stringBuilder.toString();

        // solve encoding bug
        try {
            nickname = new String(nickname.getBytes("ISO-8859-1"), "UTF-8");
            address = new String(address.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        User user = new User();
        user.setName(nickname);
        user.setHeadImg(headimgurl);
        user.setCity(address);
        user.setOpenid(openId);
        user.setSex(gender);
        user.setCreateTime(new Date());

        userMapper.save(user);

        return user;
    }
}
