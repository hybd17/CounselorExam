package com.example.exam2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.exam2.entity.Counselors;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@CacheNamespace
public interface CounselorsMapper extends BaseMapper<Counselors> {
    @Select("SELECT id FROM counselors_exam.counselors")
    List<Long> getAllIds();
}
