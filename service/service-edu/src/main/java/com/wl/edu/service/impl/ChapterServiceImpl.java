package com.wl.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wl.edu.entity.Chapter;
import com.wl.edu.entity.Video;
import com.wl.edu.entity.chapter.ChapterVo;
import com.wl.edu.entity.chapter.VideoVo;
import com.wl.edu.mapper.ChapterMapper;
import com.wl.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wl.edu.service.VideoService;
import com.wl.servicebase.exception.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2024-03-03
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoService videoService;
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String id) {
        //根据课程id查询所有的章节
        QueryWrapper<Chapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",id);
        QueryWrapper<Video> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",id);
        
        List<Chapter> chapterList = baseMapper.selectList(wrapperChapter);
        //根据课程id查询所有小节
        //这里chapter不能直接调用video的mapper,所以需要注入video的service来调用
        List<Video> videoList = videoService.list(wrapperVideo);
        //创建list集合进行最终封装
        List<ChapterVo> fianlList = new ArrayList<>();
        //遍历查询章节list进行封装
        for (int i = 0; i < chapterList.size(); i++) {
            Chapter chapter = chapterList.get(i);
            ChapterVo vo = new ChapterVo();
            BeanUtils.copyProperties(chapter,vo);

            List<VideoVo> finalVideoList = new ArrayList<>();
            for (int j = 0; j < videoList.size(); j++) {
                Video video = videoList.get(j);
                if (video.getChapterId().equals(chapter.getId())){
                    VideoVo vo1 = new VideoVo();
                    BeanUtils.copyProperties(video,vo1);
                    finalVideoList.add(vo1);
                }
            }
            vo.setChildren(finalVideoList);
            fianlList.add(vo);

        }
        //遍历查询小节list集合进行封装
        return fianlList;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
        if (count>0){
            throw new GuliException(20001,"该章节下有小节,无法删除-1");
        }else{
            int result = baseMapper.deleteById(chapterId);
            return result>0;
        }
    }
}
