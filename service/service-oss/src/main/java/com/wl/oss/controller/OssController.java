package com.wl.oss.controller;

//import com.wl.commonutils.R;
import com.wl.commonutils.R;
import com.wl.oss.service.OssServicce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssServicce ossServicce;

    @PostMapping
    public R uploadFile(MultipartFile file){
        //返回上传到oss的路径
        String url = ossServicce.uploadFileAvatar(file);
        return R.ok().data("url",url);
    }
}
