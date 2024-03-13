package com.wl.edu.controller;


import com.wl.commonutils.R;
import com.wl.edu.entity.Video;
import com.wl.edu.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author wanglei
 * @since 2024-03-03
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping("addVideo")
    public R addVideo(@RequestBody Video video){
        videoService.save(video);
        return R.ok();
    }

    //删除小节
    //TODO 后面这个方法要完善:删除小节的时候,同时删除里面的视频
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){
        videoService.removeById(id);
        return R.ok();
    }

    @GetMapping("getVideoById/{id}")
    public R getVideoById(@PathVariable String id){
        Video video = videoService.getById(id);
        return R.ok().data("video",video);
    }

    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody Video video){
        videoService.updateById(video);
        return R.ok();
    }




}

