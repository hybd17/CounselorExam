package com.example.exam2.controller;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.example.exam2.service.exception.*;
import com.example.exam2.utils.R;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

public class BaseController {

    @ExceptionHandler(ServiceException.class)
    public R handleException(Throwable e){
        if(e instanceof EnrollException){
            return R.error().message("注册时出现错误").code(300);
        }else if (e instanceof LotteryException){
            return R.error().message("抽签时出现错误").code(400);
        } else if (e instanceof StatisticsException) {
            return R.error().message("统计成绩时出现未知错误").code(500);
        } else if (e instanceof RegisterException) {
            return R.error().message("注册用户已存在").code(600);
        } else if (e instanceof SelectCounselorsException) {
            return R.error().message("查找参赛辅导员时出现未知错误").code(600);
        }
        return R.error();
    }
//    public final Integer getUidFromSession(HttpSession session){
//        String uidGet = session.getAttribute("id").toString();
//        return Integer.valueOf(uidGet);
//    }
//    public final Long getSchoolIdFromToken(String token){
//        SaTokenInfo saTokenInfo = StpUtil.getTokenInfo(token);
//
//    }
}
