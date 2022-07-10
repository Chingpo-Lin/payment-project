package com.example.paymentdemo.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.paymentdemo.config.WeChatConfig;
import com.example.paymentdemo.domain.User;
import com.example.paymentdemo.domain.Video;
import com.example.paymentdemo.domain.VideoOrder;
import com.example.paymentdemo.dto.VideoOrderDto;
import com.example.paymentdemo.mapper.UserMapper;
import com.example.paymentdemo.mapper.VideoMapper;
import com.example.paymentdemo.mapper.VideoOrderMapper;
import com.example.paymentdemo.service.VideoOrderService;
import com.example.paymentdemo.utils.CommonUtils;
import com.example.paymentdemo.utils.HttpUtils;
import com.example.paymentdemo.utils.WXPayUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Service
public class VideoOrderServiceImpl implements VideoOrderService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Logger dataLogger = LoggerFactory.getLogger("dataLogger");

    @Autowired
    private WeChatConfig weChatConfig;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private VideoOrderMapper videoOrderMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String save(VideoOrderDto videoOrderDto) throws Exception {

        dataLogger.info("module=video_order`api=save`user_id={}`video_id={}",videoOrderDto.getUserId(),videoOrderDto.getVideoId());


        // find video info
        Video video = videoMapper.findById(videoOrderDto.getVideoId());

        // user info
        User user = userMapper.findById(videoOrderDto.getUserId());

        // create order
        VideoOrder videoOrder = new VideoOrder();
        videoOrder.setTotalFee(video.getPrice());
        videoOrder.setVideoImg(video.getCoverImg());
        videoOrder.setVideoTitle(video.getTitle());
        videoOrder.setCreateTime(new Date());
        videoOrder.setVideoId(video.getId());

        videoOrder.setState(0);
        videoOrder.setUserId(user.getId());
        videoOrder.setHeadImg(user.getHeadImg());
        videoOrder.setNickname(user.getName());

        videoOrder.setDel(0);
        videoOrder.setIp(videoOrderDto.getIp());
        videoOrder.setOutTradeNo(CommonUtils.generateUUID());

        videoOrderMapper.insert(videoOrder);

        unifiedOrder(videoOrder);

        // code url
        String codeUrl = unifiedOrder(videoOrder);

        return codeUrl;
    }

    @Override
    public VideoOrder findByOutTradeNo(String outTradeNo) {
        return videoOrderMapper.findByOutTradeNo(outTradeNo);
    }

    @Override
    public int updateVideoOderByOutTradeNo(VideoOrder videoOrder) {
        return videoOrderMapper.updateVideoOderByOutTradeNo(videoOrder);
    }

    /**
     * create sign and unify order
     * @param videoOrder
     * @return
     */
    private String unifiedOrder(VideoOrder videoOrder) throws Exception {

        // create sign (required from wechat document)
        SortedMap<String, String> params = new TreeMap<>();
        params.put("appid", weChatConfig.getAppId());
        params.put("mch_id", weChatConfig.getMchId());
        params.put("nonce_str",CommonUtils.generateUUID());
        params.put("body",videoOrder.getVideoTitle());
        params.put("out_trade_no",videoOrder.getOutTradeNo());
        params.put("total_fee",videoOrder.getTotalFee().toString());
        params.put("spbill_create_ip",videoOrder.getIp());
        params.put("notify_url",weChatConfig.getPayCallbackUrl());
        params.put("trade_type","NATIVE");

        // sign
        String sign = WXPayUtil.createSign(params, weChatConfig.getKey());
        params.put("sign",sign);

        // map to xml
        String payXml = WXPayUtil.mapToXml(params);
        // check website: https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=20_1
        System.out.println(payXml);

        // order
        String orderStr = HttpUtils.doPost(WeChatConfig.getUnifiedOrderUrl(), payXml, 4000);
        System.out.println("orderStr:" + orderStr);
        if (null == orderStr) return null;

        Map<String, String> unifiedOrderMap = WXPayUtil.xmlToMap(orderStr);
        System.out.println(unifiedOrderMap.toString());
        if (unifiedOrderMap != null) {
            return unifiedOrderMap.get("code_url");
        }


        return null;
    }
}
