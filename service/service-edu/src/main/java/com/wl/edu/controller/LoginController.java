package com.wl.edu.controller;

import com.wl.commonutils.R;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/eduservice/user")
public class LoginController {

    //login
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }
    //info
    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
