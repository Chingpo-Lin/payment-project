package com.example.paymentdemo.controller;

import com.example.paymentdemo.dto.VideoOrderDto;
import com.example.paymentdemo.service.VideoOrderService;
import com.example.paymentdemo.utils.IpUtils;
import com.example.paymentdemo.utils.JsonData;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import org.mybatis.logging.*;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/api/v1/order")
//@RequestMapping("/api/v1/order")
public class OrderController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Logger dataLogger = LoggerFactory.getLogger("dataLogger");

    @Autowired
    private VideoOrderService videoOrderService;

    @GetMapping("add")
    public void saveOrder(@RequestParam(value = "video_id",required = true)int videoId,
                          HttpServletRequest request,
                          HttpServletResponse response) throws Exception {

        String ip = IpUtils.getIpAddr(request); // get user ip
        ip = "120.25.1.43"; // fixed for local test
        int userId = 1; // // fixed for local test
        VideoOrderDto videoOrderDto = new VideoOrderDto();
        videoOrderDto.setUserId(userId);
        videoOrderDto.setVideoId(videoId);
        videoOrderDto.setIp(ip);

        String codeUrl = videoOrderService.save(videoOrderDto);
        if (codeUrl == null) {
            throw new NullPointerException();
        }

        // generate qrcode
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();

            // mistake level
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            // encoding type
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            BitMatrix bitMatrix = new MultiFormatWriter().encode(codeUrl, BarcodeFormat.QR_CODE, 400, 400, hints);

            OutputStream outputStream = response.getOutputStream();

            MatrixToImageWriter.writeToStream(bitMatrix, "png", outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
