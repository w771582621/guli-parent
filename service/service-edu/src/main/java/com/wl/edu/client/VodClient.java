package com.wl.edu.client;

import com.wl.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {
    //定义要调用的方法的路径
    @DeleteMapping("/eduvod/video/{id}")
    public R deleteAlVideo(@PathVariable(value = "id") String id);
    @DeleteMapping("/eduvod/video/deleteVideosByCourse")
    public R deleteVideos(@RequestParam("videoList") List<String> videoList);
}
