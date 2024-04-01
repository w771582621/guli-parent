package com.wl.ucenter.service;

import com.wl.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wl.ucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author wanglei
 * @since 2024-03-31
 */
public interface MemberService extends IService<Member> {

    String login(Member member);

    void register(RegisterVo registerVo);
}
