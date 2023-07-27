package com.example.exam2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.exam2.entity.Scores;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
@CacheNamespace
public interface ScoresMapper extends BaseMapper<Scores> {
    @Select("SELECT * FROM counselors_exam.scores WHERE counselor_id = #{counselorId}")
    String selectByCounselorId(@Param("counselorId") Long counselorId);
}
