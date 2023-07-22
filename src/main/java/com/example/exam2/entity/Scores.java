package com.example.exam2.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("scores")
public class Scores {
    @TableId(type = IdType.AUTO)
    private long id;
    private long schoolId;
    private long counselorId;
    private String grouping;
    private String drawNumber;
    private Integer writtenTestScore;
    private Integer discussionScore;
    private Integer interviewScore;
    private Integer finalScore;
}
