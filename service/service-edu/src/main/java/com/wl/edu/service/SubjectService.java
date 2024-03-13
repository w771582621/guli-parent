package com.wl.edu.service;

import com.wl.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wl.edu.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author wanglei
 * @since 2024-03-02
 */
public interface SubjectService extends IService<Subject> {

    void saveSubject(MultipartFile file,SubjectService service);

    List<OneSubject> getAllOneSubject();
}
