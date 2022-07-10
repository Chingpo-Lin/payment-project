package com.example.paymentdemo.service;

import com.example.paymentdemo.domain.VideoOrder;
import com.example.paymentdemo.dto.VideoOrderDto;
import org.apache.ibatis.annotations.Param;

/**
 * order interface
 */
public interface VideoOrderService {

    /**
     * place order interface
     * @param videoOrderDto
     * @return
     */
    String save(VideoOrderDto videoOrderDto) throws Exception;

    /**
     * find order by trade no
     * @param outTradeNo
     * @return
     */
    VideoOrder findByOutTradeNo(String outTradeNo);

    /**
     * update order by trade no
     * @param videoOrder
     * @return
     */
    int updateVideoOderByOutTradeNo(VideoOrder videoOrder);
}
