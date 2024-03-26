package com.wl.edu.service.impl;

import com.wl.edu.client.VodClient;
import com.wl.edu.entity.Video;
import com.wl.edu.mapper.VideoMapper;
import com.wl.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2024-03-03
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private VodClient vodClient;
    @Override
    public void deleteVideoAndAlyVideo(String id) {
        Video video = baseMapper.selectById(id);
        String alyVideoId = video.getVideoSourceId();
        if (!StringUtils.isEmpty(alyVideoId)){
            vodClient.deleteAlVideo(alyVideoId);
        }
        baseMapper.deleteById(id);
    }
}
