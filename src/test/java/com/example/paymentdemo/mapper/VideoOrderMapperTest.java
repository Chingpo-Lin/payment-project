package com.example.paymentdemo.mapper;

import com.example.paymentdemo.domain.VideoOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VideoOrderMapperTest {

    @Autowired
    private VideoOrderMapper videoOrderMapper;

    @Test
    public void insert() {
        VideoOrder videoOrder = new VideoOrder();
        videoOrder.setDel(0);
        videoOrder.setTotalFee(111);
        videoOrder.setHeadImg("wwdawdd");
        videoOrder.setVideoTitle("springboot video");
        videoOrderMapper.insert(videoOrder);
        assertNotNull(videoOrder.getId());
    }

    @Test
    public void findById() {
        VideoOrder videoOrder = videoOrderMapper.findById(1);
    }

    @Test
    public void findByOutTradeNo() {
    }

    @Test
    public void del() {
    }

    @Test
    public void findMyOrderList() {
    }

    @Test
    public void updateVideoOderByOutTradeNo() {
    }
}