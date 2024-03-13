package com.wl.edu.controller;


import com.wl.commonutils.R;
import com.wl.edu.entity.subject.OneSubject;
import com.wl.edu.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author wanglei
 * @since 2024-03-02
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class SubjectController {

    @Autowired
    private SubjectService service;

    //添加课程分类
    //获取到上传过来的文件,把文件内容读取出来
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){

        service.saveSubject(file,service);
        return R.ok();
    }

    //课程分类的列表功能
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        List<OneSubject> list = service.getAllOneSubject();
        return R.ok().data("list",list);
    }
}

