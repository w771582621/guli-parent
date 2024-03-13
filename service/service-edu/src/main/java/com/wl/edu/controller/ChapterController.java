package com.wl.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wl.commonutils.R;
import com.wl.edu.entity.Chapter;
import com.wl.edu.entity.chapter.ChapterVo;
import com.wl.edu.service.ChapterService;
import com.wl.edu.service.VideoService;
import com.wl.servicebase.exception.GuliException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@Api(description = "章节管理")
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class ChapterController {
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private VideoService videoService;

    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId) {
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("list", list);
    }

    //添加章节
    @PostMapping("addChapter")
    public R addChapter(@RequestBody Chapter chapter) {
        chapterService.save(chapter);
        return R.ok();
    }

    //根据章节id查询
    @GetMapping("getChapterInfoById/{chapterId}")
    public R getChapterInfoById(@PathVariable String chapterId) {
        Chapter chapter = chapterService.getById(chapterId);
        return R.ok().data("chapter", chapter);
    }

    //修改章节
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody Chapter chapter) {
        chapterService.updateById(chapter);
        return R.ok();
    }

    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){
        boolean result = chapterService.deleteChapter(chapterId);
        return R.ok().data("result",result);
    }

}

