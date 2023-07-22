package com.example.exam2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.exam2.entity.Counselors;
import com.example.exam2.entity.FirstRound;
import com.example.exam2.entity.Scores;
import com.example.exam2.entity.SecondRound;
import com.example.exam2.mapper.CounselorsMapper;
import com.example.exam2.mapper.FirstRoundMapper;
import com.example.exam2.mapper.ScoresMapper;
import com.example.exam2.mapper.SecondRoundMapper;
import com.example.exam2.service.LotteryService;
import com.example.exam2.service.exception.LotteryException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class LotteryServiceImpl extends ServiceImpl<CounselorsMapper,Counselors> implements LotteryService {

    @Resource
    private FirstRoundMapper firstRoundMapper;
    @Resource
    private CounselorsMapper counselorsMapper;
    @Resource
    private ScoresMapper scoresMapper;
    @Resource
    private SecondRoundMapper secondRoundMapper;

    @Override
    public boolean doFirstLottery() {
        try {
            //查找数目便于进行抽签
            int count1 = counselorsMapper.selectCount(new QueryWrapper<Counselors>().eq("grouping","本科组"));
            int count2 = counselorsMapper.selectCount(new QueryWrapper<Counselors>().eq("grouping","高职高专组"));
            //将counselors进行分类添加到列表中
            QueryWrapper<Counselors> queryWrapper1 = new QueryWrapper<>();
            List<Counselors> counselor1 = counselorsMapper.selectList(queryWrapper1.eq("grouping","本科组"));
            QueryWrapper<Counselors> queryWrapper2 = new QueryWrapper<>();
            List<Counselors> counselor2 = counselorsMapper.selectList(queryWrapper2.eq("grouping","高职高专组"));
            //进行随机数生成
            List<Integer> list1 = new ArrayList<>();
            List<Integer> list2 = new ArrayList<>();
            for (int i = 1;i<=count1;i++)
                list1.add(i);
            for (int i = 1;i<=count2;i++)
                list2.add(i);
            //分类进行注入
            for (Counselors counselors:counselor1){
                FirstRound firstRound = new FirstRound();
                firstRound.setCounselorId(counselors.getId());
                String number = getGrouping(list1);
                firstRound.setDrawNumber("Bk"+number);
                firstRoundMapper.insert(firstRound);
            }
            for (Counselors counselors:counselor2){
                FirstRound firstRound = new FirstRound();
                firstRound.setCounselorId(counselors.getId());
                String number = getGrouping(list2);
                firstRound.setDrawNumber("GZ"+number);
                firstRoundMapper.insert(firstRound);
            }
        } catch (Exception e) {
            throw new LotteryException(e);
        }
        return true;
    }

    @Override
    public boolean doSecondLottery(String grouping) {
        try {
            List<Scores> scoresList = scoresMapper.selectList(new QueryWrapper<Scores>()
                                                    .eq("grouping",grouping)
                                                    .orderByDesc("written_test_score").last("LIMIT 30"));
            List<Integer> numbers = new ArrayList<>();
            for (int i = 1;i<=15;i++)
                numbers.add(i);
            int index = 0;
            while(!scoresList.isEmpty()){
                Collections.shuffle(scoresList);
                Scores randomScore = scoresList.get(0);
                int cnt = 0;
                for(Scores scores:scoresList){
                    if(scores.getSchoolId()!=randomScore.getSchoolId()){
                        //进行组号
                        SecondRound secondRound = new SecondRound();
                        secondRound.setCounselorId(scores.getCounselorId());
                        secondRound.setDrawNumber("A"+numbers.get(index).toString());
                        secondRoundMapper.insert(secondRound);
                        SecondRound secondRound2 = new SecondRound();
                        secondRound2.setCounselorId(randomScore.getCounselorId());
                        secondRound2.setDrawNumber("B"+numbers.get(index).toString());
                        secondRoundMapper.insert(secondRound2);
                        index++;
                        //从列表中剔除
                        scoresList.remove(scores);
                        scoresList.remove(randomScore);
                        cnt++;
                    }
                    if (cnt>=1)
                        break;
                }
            }
        } catch (Exception e) {
            throw new LotteryException(e);
        }
        return true;
    }

    public static String getGrouping(List<Integer> list){
        Random random = new Random();
        int randomIndex = random.nextInt(list.size());
        int randomElement = list.get(randomIndex);
        list.remove(randomIndex);
        return String.valueOf(randomElement);
    }
}
