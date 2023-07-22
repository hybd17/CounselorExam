package com.example.exam2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.exam2.entity.FirstRound;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface FirstRoundMapper extends BaseMapper<FirstRound> {
    @Select("SELECT draw_number FROM counselors_exam.first_round WHERE counselor_id = #{counselorId} LIMIT 1")
    String getGroupingByCounselorId(@Param("counselorId") Long counselorId);
}
