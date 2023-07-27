package com.example.exam2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.exam2.entity.SecondRound;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@CacheNamespace
public interface SecondRoundMapper extends BaseMapper<SecondRound> {
}
