package com.example.exam2.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.exam2.entity.Counselors;
import com.example.exam2.entity.Scores;

import java.util.List;

public interface LotteryService extends IService<Counselors> {
    boolean doFirstLottery();
    boolean doSecondLottery(String grouping);
}
