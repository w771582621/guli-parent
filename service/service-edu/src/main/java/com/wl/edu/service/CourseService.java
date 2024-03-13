package com.wl.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wl.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wl.edu.entity.CourseQuery;
import com.wl.edu.entity.vo.CourceInfoVo;
import com.wl.edu.entity.vo.PublishCourseVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author wanglei
 * @since 2024-03-03
 */
public interface CourseService extends IService<Course> {

    String saveCourceInfo(CourceInfoVo courceInfoVo);

    CourceInfoVo getCourseInfoById(String courseId);

    void updateCourseInfo(CourceInfoVo vo);

    PublishCourseVo getPublishCourseInfo(String id);

    void getCoursePage(Page<Course> pageParam, CourseQuery query);
}
