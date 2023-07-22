package com.example.exam2.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.exam2.entity.Scores;
import com.example.exam2.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    //注册账号
    boolean register(User user);
    //登录账号
    boolean login(String username,String password);
    //辅导员报名
    boolean enroll(long schoolId,String name,String idNumber,String grouping);
    User selectByUsername(String username);
    Integer getCountByGrouping(String grouping);
    boolean writingScoreImport(List<Integer> score,String grouping);
    boolean interviewScoreImport(Scores scores, List<Integer> interviewScore);
    List<Integer> generateRandomNumbers(int n, int lowerBound, int upperBound);
    boolean discussionScoreImport(Scores scores, List<Integer> discussionScore,String word);
    List<Scores> generateCounselors(String grouping);//取出笔试成绩前百分之三十的辅导员
    List<Scores> findAllCounselors();
    List<String> getAllName();
    boolean finalScoreImport(Scores scores);
}
