package com.wl.ucenter.controller;


import com.wl.commonutils.JwtUtils;
import com.wl.commonutils.R;
import com.wl.ucenter.entity.Member;
import com.wl.ucenter.entity.vo.RegisterVo;
import com.wl.ucenter.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author wanglei
 * @since 2024-03-31
 */
@RestController
@RequestMapping("/eduucenter/member")
@CrossOrigin
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping("login")
    public R loginUser(@RequestBody Member member) {
        //返回token值,使用jwt生成
        return R.ok().data("token", memberService.login(member));
    }

    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return R.ok();
    }

    //根据token获取用户信息
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        Member member = memberService.getById(memberId);
        return R.ok().data("userInfo",member);
    }
}

