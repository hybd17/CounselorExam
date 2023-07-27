package com.example.exam2.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("first_round")
public class FirstRound implements Serializable {
    @TableId(type = IdType.AUTO)
    private long id;
    private long counselorId;
    private String drawNumber;
}
