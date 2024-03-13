package com.wl.edu.mapper;

import com.wl.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wl.edu.entity.vo.PublishCourseVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author wanglei
 * @since 2024-03-03
 */
public interface CourseMapper extends BaseMapper<Course> {

    PublishCourseVo getPublishCourseVo(String id);

}
