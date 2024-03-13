package com.wl.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wl.commonutils.R;
import com.wl.edu.entity.Course;
import com.wl.edu.entity.CourseQuery;
import com.wl.edu.entity.vo.CourceInfoVo;
import com.wl.edu.entity.vo.PublishCourseVo;
import com.wl.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author wanglei
 * @since 2024-03-03
 */
@Api(description = "课程管理")
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("addCourceInfo")
    public R addCourceInfo(@RequestBody CourceInfoVo courceInfoVo){
        String id = courseService.saveCourceInfo(courceInfoVo);
        return R.ok().data("courseId",id);
    }

    @GetMapping("getCourseInfoById/{courseId}")
    public R getCOurseInfoById(@PathVariable String courseId){
        CourceInfoVo courseInfoById = courseService.getCourseInfoById(courseId);
        return R.ok().data("courseInfoVo",courseInfoById);
    }

    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourceInfoVo vo){
        courseService.updateCourseInfo(vo);
        return R.ok();
    }

    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
        PublishCourseVo publishCourseVo = courseService.getPublishCourseInfo(id);
        return R.ok().data("vo",publishCourseVo);
    }

    @PutMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        Course course = new Course();
        course.setId(id);
        course.setStatus("Normal");
        courseService.updateById(course);
        return R.ok();
    }

    @PostMapping("getCoursePage/{page}/{limit}")
    public R getCoursePage(@ApiParam(name = "page", value = "当前页码", required = true)
                               @PathVariable Long page,
                           @ApiParam(name = "limit", value = "每页记录数", required = true)
                           @PathVariable Long limit,
                           @ApiParam(name = "courseQuery", value = "查询对象", required = false)
                           @RequestBody(required = false) CourseQuery query){
        Page<Course> pageParam = new Page<>(page,limit);
        courseService.getCoursePage(pageParam,query);
        List<Course> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return R.ok().data("records",records).data("total",total);
    }
}

