package com.wl.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssServicce {
    String uploadFileAvatar(MultipartFile file);
}
