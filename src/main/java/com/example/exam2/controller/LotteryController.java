package com.example.exam2.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.example.exam2.service.LotteryService;
import com.example.exam2.utils.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
    @RequestMapping("lottery")
public class LotteryController {

    @Resource
    private LotteryService lotteryService;

    @PostMapping("/writingLottery")
    @SaCheckPermission(orRole = {"BKadmin","GZadmin"})
    public R WritingLottery(){
        lotteryService.doFirstLottery();
        return R.ok().message("笔试分组成功");
    }
    @PostMapping("/discussionBKLottery")
    @SaCheckRole("BKadmin")
    public R DiscussionBKLottery(){
        lotteryService.doSecondLottery("本科组");
        return R.ok().message("案例研讨本科组分组成功");
    }
    @PostMapping("/discussionGZLottery")
    @SaCheckRole("GZadmin")
    public R DiscussionGZLottery(){
        lotteryService.doSecondLottery("高职高专组");
        return R.ok().message("案例研讨高职高专组分组成功");
    }
}
