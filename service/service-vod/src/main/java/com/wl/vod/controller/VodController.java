package com.wl.vod.controller;

import com.wl.commonutils.R;
import com.wl.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;
    @PostMapping("upload")
    public R uploadVideo(MultipartFile file){
        String videoId = vodService.uploadVideoFiles(file);
        return R.ok().data("videoId",videoId);
    }

    @DeleteMapping("/{id}")
    public R deleteAlVideo(@PathVariable(value = "id") String id){
        vodService.deleteVideo(id);
        return R.ok();
    }

    @DeleteMapping("deleteVideosByCourse")
    public R deleteVideos(@RequestParam("videoList")List<String> videoList){
        vodService.deleteVideoByCourse(videoList);
        return R.ok();
    }

}
