package com.wl.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wl.commonutils.JwtUtils;
import com.wl.commonutils.MD5;
import com.wl.servicebase.exception.GuliException;
import com.wl.ucenter.entity.Member;
import com.wl.ucenter.entity.vo.RegisterVo;
import com.wl.ucenter.mapper.MemberMapper;
import com.wl.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2024-03-31
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public String login(Member member) {
        String mobile = member.getMobile();
        String password = member.getPassword();

        //手机号和密码非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new GuliException(20001,"登录失败!");
        }

        //判断手机号是否正确
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Member mobileMember = baseMapper.selectOne(wrapper);
        //判断查询出的对象是否为空
        if (mobileMember == null){
            throw new GuliException(20001,"未查询到手机号!");
        }
        //判断密码
        //数据库中的密码是加密过的，所以就要把输入的密码进行加密，然后和数据库中的密码进行比较
        //加密方式：MD5
        if (!MD5.encrypt(password).equals(mobileMember.getPassword())){
            throw new GuliException(20001,"密码不正确！");
        }
        //判断是否被禁用
        if (mobileMember.getIsDisabled()){
            throw new GuliException(20001,"用户被禁用！");
        }
        //登陆成功
        //生成token字符串，使用jwt工具类

        return JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
    }

    @Override
    public void register(RegisterVo registerVo) {
        //获取注册信息，进行校验
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //校验参数
        if(StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code)) {
            throw new GuliException(20001,"error");
        }

        //校验校验验证码
        //从redis获取发送的验证码
        String mobleCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(mobleCode)) {
            throw new GuliException(20001,"error");
        }

        //查询数据库中是否存在相同的手机号码
        Integer count = baseMapper.selectCount(new QueryWrapper<Member>().eq("mobile", mobile));
        if(count.intValue() > 0) {
            throw new GuliException(20001,"该手机已被注册！");
        }

        //添加注册信息到数据库
        Member member = new Member();
        member.setNickname(nickname);
        member.setMobile(registerVo.getMobile());
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("");
        baseMapper.insert(member);
    }
}
