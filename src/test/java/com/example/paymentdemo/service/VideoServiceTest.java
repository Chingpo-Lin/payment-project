package com.example.paymentdemo.service;

import com.example.paymentdemo.domain.Video;
import com.example.paymentdemo.mapper.VideoMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class VideoServiceTest {

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoMapper videoMapper;

    @Test
    void findAll() {
        List<Video> list = videoService.findAll();
        assertNotNull(list);
        for (Video video : list) {
            System.out.println(video.getTitle());
        }
    }

    @Test
    void findById() {
        Video video = videoService.findById(2);
        assertNotNull(video);
        System.out.println(video.getTitle());
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void save() {
    }
}