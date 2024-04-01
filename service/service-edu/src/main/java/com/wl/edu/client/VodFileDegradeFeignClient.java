package com.wl.edu.client;

import com.wl.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public R deleteAlVideo(String id) {
        return R.error().message("删除视频出错");
    }

    @Override
    public R deleteVideos(List<String> videoList) {
        return R.error().message("删除多个视频出错");
    }
}
