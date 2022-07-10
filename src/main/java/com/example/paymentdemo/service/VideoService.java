package com.example.paymentdemo.service;

import com.example.paymentdemo.domain.Video;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * video interface
 */
public interface VideoService {

    List<Video> findAll();

    Video findById(int id);

    int update(Video video);

    int delete(int id);

    int save(Video video);
}
