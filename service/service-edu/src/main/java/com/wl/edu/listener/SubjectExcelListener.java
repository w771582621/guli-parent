package com.wl.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wl.edu.entity.Subject;
import com.wl.edu.entity.excel.SubjectData;
import com.wl.edu.service.SubjectService;
import com.wl.servicebase.exception.GuliException;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    //因为SubjectListener不能交给Spring来管理,需要自己new,不能注入其他对象
    //不能实现数据库操作
    public SubjectService subjectService;


    public SubjectExcelListener() {
    }

    public SubjectExcelListener(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    //读取excel的内容,一行一行进行读取
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null) {
            throw new GuliException(20001, "文件数据为空");
        }

        Subject oneSubject = existOneSubject(subjectService, subjectData.getOneSubjectName());
        //判断一级分类是否重复
        if ( oneSubject== null){
            oneSubject = new Subject();
            oneSubject.setTitle(subjectData.getOneSubjectName());
            oneSubject.setParentId("0");
            subjectService.save(oneSubject);
        }
        String pid = oneSubject.getId();
        //判断二级分类是否重复
        Subject twoSubject = existTwoSubject(subjectService, subjectData.getTwoSubjectName(), oneSubject.getParentId());
        if (twoSubject == null){
            twoSubject = new Subject();
            twoSubject.setTitle(subjectData.getTwoSubjectName());
            twoSubject.setParentId(pid);
            subjectService.save(twoSubject);
        }
    }


    private Subject existOneSubject(SubjectService subjectService,String title){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("title",title);
        wrapper.eq("parent_id","0");
        Subject one = subjectService.getOne(wrapper);
        return one;
    }

    private Subject existTwoSubject(SubjectService service,String title,String pid){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("title",title);
        wrapper.eq("parent_id",pid);
        Subject two = subjectService.getOne(wrapper);
        return two;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
