package com.example.exam2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.exam2.entity.User;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
@CacheNamespace//实现二级缓存，将查询的值储存到redis中
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT role FROM counselors_exam.user WHERE username = #{username}")
    List<String> selectRolesByUsername(@Param("username") String username);
}
