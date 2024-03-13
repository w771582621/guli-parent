package com.wl.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wl.commonutils.R;
import com.wl.edu.entity.Teacher;
import com.wl.edu.query.TeacherQuery;
import com.wl.edu.service.TeacherService;
import com.wl.servicebase.exception.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author wanglei
 * @since 2024-02-18
 */
@Api(description = "讲师管理")
@CrossOrigin //跨域
@RestController
@RequestMapping("/eduservice/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R list() {
//        try {
//            int a = 10/0;
//        }catch (Exception e){
//            throw new GuliException(20001,"出现自定义异常");
//        }

        List<Teacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {
        teacherService.removeById(id);
        return R.ok();
    }

    @ApiOperation(value = "分页讲师列表")
    @GetMapping("pageTeacher/{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {

        Page<Teacher> pageParam = new Page<>(page, limit);

        teacherService.page(pageParam, null);
        List<Teacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();

        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "条件分页讲师列表")
    @PostMapping("pageTeacherCondition/{page}/{limit}")
    public R pageListCondition(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "teacherQuery", value = "查询对象", required = false)
            @RequestBody(required = false) TeacherQuery teacherQuery) {


        Page<Teacher> pageParam = new Page<>(page, limit);

        teacherService.pageQuery(pageParam, teacherQuery);
        List<Teacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();

        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "新增讲师")
    @PostMapping("addTeacher")
    public R save(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody Teacher teacher) {
        boolean save = teacherService.save(teacher);
        return save ? R.ok() : R.error();
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("getTeacher/{id}")
    public R getById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {

        Teacher teacher = teacherService.getById(id);
        return R.ok().data("item", teacher);
    }

    @ApiOperation(value = "修改讲师")
    @PostMapping("updateTeacher")
    public R updateTeacher(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody Teacher teacher) {
        boolean flag = teacherService.updateById(teacher);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }
}

