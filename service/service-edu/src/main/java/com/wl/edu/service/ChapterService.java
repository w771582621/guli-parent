package com.wl.edu.service;

import com.wl.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wl.edu.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author wanglei
 * @since 2024-03-03
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterVo> getChapterVideoByCourseId(String id);

    boolean deleteChapter(String chapterId);
}
