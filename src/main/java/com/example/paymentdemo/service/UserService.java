package com.example.paymentdemo.service;

import com.example.paymentdemo.domain.User;

public interface UserService {

    User saveWeChatUser(String code);
}

