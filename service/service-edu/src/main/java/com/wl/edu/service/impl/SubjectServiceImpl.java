package com.wl.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wl.edu.entity.Subject;
import com.wl.edu.entity.excel.SubjectData;
import com.wl.edu.entity.subject.OneSubject;
import com.wl.edu.entity.subject.TwoSubject;
import com.wl.edu.listener.SubjectExcelListener;
import com.wl.edu.mapper.SubjectMapper;
import com.wl.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2024-03-02
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,SubjectService service) {
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(service)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public List<OneSubject> getAllOneSubject() {
        QueryWrapper<Subject> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("parent_id","0");
        QueryWrapper<Subject> wrapper2 = new QueryWrapper<>();
        wrapper2.ne("parent_id","0");
        List<OneSubject> finalSunject = new ArrayList<>();
        //查询所有一级分类
        List<Subject> listOne = baseMapper.selectList(wrapper1);
        //查询所有二级分类
        List<Subject> listTwo = baseMapper.selectList(wrapper2);
        //将一级分类存入最终list
        for (int i = 0; i < listOne.size(); i++) {
            Subject subject = listOne.get(i);
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(subject,oneSubject);

            List<TwoSubject> finalTwoList = new ArrayList<>();
            //将对应的二级分类存入一级分类的list
            for (int i1 = 0; i1 < listTwo.size(); i1++) {
                Subject subject1 = listTwo.get(i1);
                if (subject.getId().equals(subject1.getParentId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(subject1,twoSubject);
                    finalTwoList.add(twoSubject);
                }
            }
            oneSubject.setChildren(finalTwoList);
            finalSunject.add(oneSubject);
        }

        //创建list集合,存放最终的封装数据
        return finalSunject;
    }
}
