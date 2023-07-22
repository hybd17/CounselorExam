package com.example.exam2.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("counselors")
public class Counselors {
    @TableId(type = IdType.AUTO)
    private long id;
    private long schoolId;
    private String name;
    private String idNumber;
    private String grouping;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date registrationTime;//报名时间
}
