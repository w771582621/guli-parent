package com.wl.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface VodService {
    String uploadVideoFiles(MultipartFile file);

    void deleteVideo(String id);

    void deleteVideoByCourse(List<String> videoList);
}
