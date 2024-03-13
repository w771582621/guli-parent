package com.wl.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wl.edu.entity.Course;
import com.wl.edu.entity.CourseDescription;
import com.wl.edu.entity.CourseQuery;
import com.wl.edu.entity.vo.CourceInfoVo;
import com.wl.edu.entity.vo.PublishCourseVo;
import com.wl.edu.mapper.CourseMapper;
import com.wl.edu.service.CourseDescriptionService;
import com.wl.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wl.servicebase.exception.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2024-03-03
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionService courseDescriptionService;
    @Override
    public String saveCourceInfo(CourceInfoVo courceInfoVo) {
        //向课程表添加课程基本信息
        //CourceInfoVo转换为Cource对象
        Course cource = new Course();
        BeanUtils.copyProperties(courceInfoVo,cource);
        int insert = baseMapper.insert(cource);

        if(insert<=0){
            //添加失败
            throw new GuliException(20001,"添加课程信息失败");
        }
        //获取添加课程之后的id
        String cid = cource.getId();

        //向课程简介表添加课程简介
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courceInfoVo.getDescription());
        courseDescription.setId(cid);
        courseDescriptionService.save(courseDescription);
        return cid;
    }

    @Override
    public CourceInfoVo getCourseInfoById(String courseId) {
        Course course = baseMapper.selectById(courseId);
        CourseDescription courseDescription = courseDescriptionService.getById(courseId);
        CourceInfoVo vo = new CourceInfoVo();
        BeanUtils.copyProperties(course,vo);
        vo.setDescription(courseDescription.getDescription());
        return vo;
    }

    @Override
    public void updateCourseInfo(CourceInfoVo vo) {
        Course course = new Course();
        BeanUtils.copyProperties(vo,course);
        int update = baseMapper.updateById(course);
        if ((update == 0)){
            throw new GuliException(20001,"修改课程信息失败");
        }
        CourseDescription description = new CourseDescription();
        description.setId(vo.getId());
        description.setDescription(vo.getDescription());
        boolean update1 = courseDescriptionService.updateById(description);
        if (!update1){
            throw new GuliException(20001,"修改课程描述失败");
        }
    }

    @Override
    public PublishCourseVo getPublishCourseInfo(String id) {
        PublishCourseVo vo = baseMapper.getPublishCourseVo(id);
        return vo;
    }

    @Override
    public void getCoursePage(Page<Course> pageParam, CourseQuery query) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("status","Normal");
        wrapper.orderByDesc("gmt_create");
        if (query == null){
            baseMapper.selectPage(pageParam,wrapper);
            return;
        }

        String title = query.getTitle();
        String teacherId = query.getTeacherId();
        String subjectParentId = query.getSubjectParentId();
        String subjectId = query.getSubjectId();

        if (!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if (!StringUtils.isEmpty(teacherId)){
            wrapper.eq("teacher_id",teacherId);
        }
        if (!StringUtils.isEmpty(subjectParentId)){
            wrapper.eq("subject_parent_id",subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)){
            wrapper.eq("subject_id",subjectId);
        }


        baseMapper.selectPage(pageParam,wrapper);

    }
}
