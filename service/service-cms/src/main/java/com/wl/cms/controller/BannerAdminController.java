package com.wl.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wl.commonutils.R;
import com.wl.cms.entity.CrmBanner;
import com.wl.cms.service.CrmBannerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author wanglei
 * @since 2024-03-24
 */
@RestController
@RequestMapping("/educms/admin/banner")
@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation(value = "获取Banner")
    @GetMapping("getallbanner/{id}")
    public R getBanner(@PathVariable("id") String id){
        CrmBanner byId = bannerService.getById(id);
        return R.ok().data("item",byId);
    }

    @ApiOperation(value = "获取Banner分页列表")
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit){
        Page<CrmBanner> pageParam = new Page<>(page,limit);
        bannerService.page(pageParam,null);
        return R.ok().data("records",pageParam.getRecords()).data("total",pageParam.getTotal());
    }

    @ApiOperation(value = "新增Banner")
    @PostMapping("addbanner")
    public R addBanner(@RequestBody CrmBanner banner){
        bannerService.save(banner);
        return R.ok();
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("updatebanner")
    public R updateBanner(@RequestBody CrmBanner banner){
        bannerService.updateById(banner);
        return R.ok();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("{id}")
    public R deleteBanner(@PathVariable String id){
        bannerService.removeById(id);
        return R.ok();
    }
}

