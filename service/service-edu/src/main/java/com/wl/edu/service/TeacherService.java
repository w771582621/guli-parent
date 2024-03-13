package com.wl.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wl.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wl.edu.query.TeacherQuery;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author wanglei
 * @since 2024-02-18
 */
public interface TeacherService extends IService<Teacher> {

    void pageQuery(Page<Teacher> pageParam, TeacherQuery teacherQuery);
}
