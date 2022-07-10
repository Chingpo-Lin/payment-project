package com.example.paymentdemo.controller.admin;

import com.example.paymentdemo.domain.Video;
import com.example.paymentdemo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/api/v1/video")
public class VideoAdminController {

    @Autowired
    private VideoService videoService;

    /**
     * delete by given id
     * @param videoId
     * @return
     */
    @DeleteMapping("del_by_id")
    public Object delById(@RequestParam(value = "video_id", required = true) int videoId) {
        return videoService.delete(videoId);
    }

    /**
     * update video object
     * @param video
     * @return
     */
    @PutMapping("update_by_id")
    public Object updateById(@RequestBody Video video) {
        return videoService.update(video);
    }

    /**
     * save video object
     * @param video
     * @return
     */
    @PostMapping("save")
    public Object saveById(@RequestBody Video video) {
        int row = videoService.save(video);
        return video.getId();
    }
}
