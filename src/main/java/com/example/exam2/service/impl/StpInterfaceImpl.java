package com.example.exam2.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import com.example.exam2.entity.User;
import com.example.exam2.mapper.UserMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class StpInterfaceImpl implements StpInterface {
    @Resource
    private UserMapper userMapper;
    @Override
    public List<String> getPermissionList(Object o, String s) {
        return null;
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        User user = userMapper.selectById(o.toString());
        List<String> roleList = new ArrayList<>();
        roleList.add(user.getRole());
        return roleList;
    }
}
