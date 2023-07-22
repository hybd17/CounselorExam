package com.example.exam2.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.exam2.entity.*;
import com.example.exam2.mapper.*;
import com.example.exam2.service.UserService;
import com.example.exam2.service.exception.EnrollException;
import com.example.exam2.service.exception.StatisticsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import javax.annotation.Resource;
import java.util.*;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private CounselorsMapper counselorsMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private ScoresMapper scoresMapper;
    @Resource
    private FirstRoundMapper firstRoundMapper;
    @Resource
    private SecondRoundMapper secondRoundMapper;
    @Autowired
    private StpInterfaceImpl stpInterface;


    @Override
    public boolean register(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",user.getUsername());
        User existingUser = getOne(queryWrapper);
        if(existingUser!=null){
            throw new RuntimeException("User already exists");
        }
        boolean save = save(user);
        return save;
    }
    //登录账号
    @Override
    public boolean login(String username, String password) {
        User existingUser = this.selectByUsername(username);
        if(existingUser!=null && existingUser.getPassword().equals(password)){
            StpUtil.login(existingUser.getId());
            stpInterface.getRoleList(existingUser.getId(),"username");
            //System.out.println("这是list"+StpUtil.getRoleList(existingUser.getId()));
            return true;
        }
        return false;
    }

    @Override
    public boolean enroll(long schoolId,String name,String idNumber,String grouping) {
        try {
            Integer count = counselorsMapper.selectCount(new QueryWrapper<Counselors>().eq("school_id", schoolId));
            if(count<3){
                Counselors counselors = new Counselors();
                counselors.setSchoolId(schoolId);
                counselors.setName(name);
                counselors.setIdNumber(idNumber);
                counselors.setGrouping(grouping);
                counselorsMapper.insert(counselors);
            }else {
                throw new EnrollException("以达到最大注册个数");
            }
        } catch (Exception e) {
            throw new EnrollException(e);
        }
        return true;
    }

    @Override
    public boolean writingScoreImport(List<Integer> score,String grouping) {
        try {
            List<Counselors> list = counselorsMapper.selectList(new QueryWrapper<Counselors>().eq("grouping", grouping));
            int index = 0;
            for(Counselors counselor:list){
                Scores scores = new Scores();
                scores.setCounselorId(counselor.getId());
                scores.setSchoolId(counselor.getSchoolId());
                scores.setDrawNumber(firstRoundMapper.getGroupingByCounselorId(counselor.getId()));
                scores.setGrouping(grouping);
                scores.setWrittenTestScore(score.get(index));
                scoresMapper.insert(scores);
                index++;
            }
        } catch (Exception e) {
            throw new StatisticsException(e);
        }
        return true;
    }


    @Override
    public boolean discussionScoreImport(Scores scores, List<Integer> discussionScore,String word) {
        try {
            Collections.sort(discussionScore);
            List<Integer> subList = discussionScore.subList(1,4);
            int sum = 0;
            for (int score:subList){
                sum += score;
            }
            int discussionAverScore = sum/3;
            UpdateWrapper<Scores> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id",scores.getId());
            updateWrapper.set(word,discussionAverScore);
            scoresMapper.update(scores,updateWrapper);
        } catch (Exception e) {
            throw new StatisticsException(e);
        }
        return true;
    }

    @Override
    public boolean interviewScoreImport(Scores scores, List<Integer> interviewScore) {
        this.discussionScoreImport(scores,interviewScore,"interview_score");
        return true;
    }

    @Override
    public Integer getCountByGrouping(String grouping) {
        return counselorsMapper.selectCount(new QueryWrapper<Counselors>().eq("grouping",grouping));
    }

    @Override
    public User selectByUsername(String username) {
        return getOne(new QueryWrapper<User>().eq("username", username));
    }

    @Override
    public List<Integer> generateRandomNumbers(int n, int lowerBound, int upperBound) {
        Random random = new Random();
        List<Integer> randomNumbers = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int randomNumber = random.nextInt(upperBound - lowerBound + 1) + lowerBound;
            randomNumbers.add(randomNumber);
        }

        return randomNumbers;
    }

    @Override
    public List<Scores> generateCounselors(String grouping) {
        List<Scores> scores =   null;
        try {
            scores = new ArrayList<>();
            QueryWrapper<SecondRound> queryWrapper = new QueryWrapper<SecondRound>();
            queryWrapper.orderByAsc("id");
            List<SecondRound> roundList = secondRoundMapper.selectList(queryWrapper);
            if(grouping.equals("本科组")){
                //分页查询
//                Page<SecondRound> pageBK = new Page<>(1,30);
//                IPage<SecondRound> resBK = secondRoundMapper.selectPage(pageBK,queryWrapper);
//                List<SecondRound> resBKRecords = resBK.getRecords();
                //List<SecondRound> list = secondRoundMapper.selectList(new QueryWrapper<SecondRound>().orderByAsc("id").last("LIMIT 30"));
                List<SecondRound> list = roundList.subList(0,30);
                for(SecondRound secondRound:list){
                    long counselorId = secondRound.getCounselorId();
                    Scores score = scoresMapper.selectOne(new QueryWrapper<Scores>().eq("counselor_id",counselorId));
                    scores.add(score);
                }
            }
            if(grouping.equals("高职高专组")){
//                Page<SecondRound> pageGZ = new Page<>(2,30);
//                IPage<SecondRound> resGZ = secondRoundMapper.selectPage(pageGZ,queryWrapper);
//                List<SecondRound> resGZRecords = resGZ.getRecords();
                //List<SecondRound> list = secondRoundMapper.selectList(new QueryWrapper<SecondRound>().orderByDesc("id").last("LIMIT 30"));
                List<SecondRound> list = roundList.subList(30,60);
                for(SecondRound secondRound:list){
                    long counselorId = secondRound.getCounselorId();
                    Scores score = scoresMapper.selectOne(new QueryWrapper<Scores>().eq("counselor_id",counselorId));
                    scores.add(score);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return scores;
    }

    @Override
    public boolean finalScoreImport(Scores scores) {
        try {
            int finalScore = (int) (scores.getWrittenTestScore()*0.2+scores.getDiscussionScore()*0.4+scores.getInterviewScore()*0.4);
            UpdateWrapper<Scores> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id",scores.getId())
                    .set("final_score",finalScore);
            scoresMapper.update(scores,updateWrapper);
        } catch (Exception e) {
            throw new StatisticsException(e);
        }
        return true;
    }

    @Override
    public List<Scores> findAllCounselors() {
        return scoresMapper.selectList(new QueryWrapper<Scores>().orderByAsc("id"));
    }

    @Override
    public List<String> getAllName() {
        List<Scores> scoresList = this.findAllCounselors();
        List<String> name = new ArrayList<>();
        for (Scores scores:scoresList){
            name.add(counselorsMapper.selectOne(new QueryWrapper<Counselors>().eq("id",scores.getCounselorId())).getName());
        }
        return name;
    }
}
