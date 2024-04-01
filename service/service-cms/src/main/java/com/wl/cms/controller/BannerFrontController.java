package com.wl.cms.controller;

import com.wl.commonutils.R;
import com.wl.cms.entity.CrmBanner;
import com.wl.cms.service.CrmBannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/educms/front/banner")
@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;
    @ApiOperation(value = "获取首页banner")
    @GetMapping("getAllBanner")
    @Cacheable(value = "banner", key = "'selectIndexList'")
    public R getAllBanner(){
        List<CrmBanner> list = bannerService.list(null);
        return R.ok().data("list",list);
    }
}
