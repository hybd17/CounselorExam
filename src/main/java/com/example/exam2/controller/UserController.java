package com.example.exam2.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.example.exam2.entity.Scores;
import com.example.exam2.entity.User;
import com.example.exam2.service.UserService;
import com.example.exam2.utils.R;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;
    @PostMapping("/register")
    public R register(@RequestBody User user){
        boolean success = userService.register(user);
        if(success)
            return R.ok();
        else
            return R.error();
    }

    @PostMapping("/login")
    public SaResult login(String username,String password){
        if(userService.login(username,password))
        {
            return SaResult.ok("成功登录");
        }
        else
            return SaResult.error("登陆失败");
    }

    @PostMapping("/enroll")
    @SaCheckLogin
    public R enroll(String name,String idNumber,String grouping){
        Long schoolID = StpUtil.getLoginIdAsLong();
        userService.enroll(schoolID,name,idNumber,grouping);
        return R.ok().message("成功注册");
    }
    @PostMapping("/writingBKScore")
    @SaCheckRole("BKadmin")
    public R writingScoreImportBK(){
        int len = userService.getCountByGrouping("本科组");
        List<Integer> scores = new ArrayList<>();
        scores = userService.generateRandomNumbers(len,75,100);
        userService.writingScoreImport(scores,"本科组");
        return R.ok();
    }
    @PostMapping("/writingGZScore")
    @SaCheckRole("GZadmin")
    public R writingScoreImportGZ(){
        int len = userService.getCountByGrouping("高职高专组");
        List<Integer> scores = new ArrayList<>();
        scores = userService.generateRandomNumbers(len,75,100);
        userService.writingScoreImport(scores,"高职高专组");
        return R.ok();
    }

    @PostMapping("/discussionBKScore")
    @SaCheckRole("BKadmin")
    public R discussionScoreImportBK(){
        List<Scores> scores = userService.generateCounselors("本科组");
        while(!scores.isEmpty()){
            Scores score1 = scores.get(0);
            userService.discussionScoreImport(score1,userService.generateRandomNumbers(5,75,100),"discussion_score");
            scores.remove(0);
        }
        //System.out.println(scores.size());
        return R.ok().message("本科组案例研讨成绩导入成功");
    }

    @PostMapping ("/discussionGZScore")
    @SaCheckRole("GZadmin")
    public R discussionScoreImportGZ(){
        List<Scores> scores = userService.generateCounselors("高职高专组");
        while(!scores.isEmpty()){
            Scores score1 = scores.get(0);
            userService.discussionScoreImport(score1,userService.generateRandomNumbers(5,75,100),"discussion_score");
            scores.remove(0);
        }
        //System.out.println(scores.size());
        return R.ok().message("高职高专组案例研讨成绩导入成功");
    }
    @PostMapping("/interviewBKScore")
    @SaCheckRole("BKadmin")
    public R interviewScoreImportBK(){
        List<Scores> scores = userService.generateCounselors("本科组");
        while(!scores.isEmpty()){
            Scores score1 = scores.get(0);
            userService.interviewScoreImport(score1,userService.generateRandomNumbers(5,75,100));
            scores.remove(0);
        }
        //System.out.println(scores.size());
        return R.ok().message("本科组谈心谈话成绩导入成功");
    }
    @PostMapping("/interviewGZScore")
    @SaCheckRole("GZadmin")
    public R interviewScoreImportGZ(){
        List<Scores> scores = userService.generateCounselors("高职高专组");
        while(!scores.isEmpty()){
            Scores score1 = scores.get(0);
            userService.interviewScoreImport(score1,userService.generateRandomNumbers(5,75,100));
            scores.remove(0);
        }
        //System.out.println(scores.size());
        return R.ok().message("高职高专组谈心谈话成绩导入成功");
    }

    @GetMapping("/sataScore")
    @SaCheckPermission(orRole = {"BKadmin","GZadmin"})
    public R SataScoreImport(){
        //userService.statScore();
        List<Scores> list = userService.findAllCounselors();
        for(Scores scores:list){
            userService.finalScoreImport(scores);
        }
        List<String> name = userService.getAllName();
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream("final.pdf"));
            document.open();
            //字体设置
            String path = "C:/Windows/Fonts/simhei.ttf";
            BaseFont bfChinese = BaseFont.createFont(path, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            Font font = new Font(bfChinese);

            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            Font headerFont = new Font(bfChinese, 12, Font.BOLD);
            PdfPCell headerCell;

            headerCell = new PdfPCell(new Paragraph("选手", headerFont));
            table.addCell(headerCell);

            headerCell = new PdfPCell(new Paragraph("参赛组别", headerFont));
            table.addCell(headerCell);

            headerCell = new PdfPCell(new Paragraph("第一轮签号", headerFont));
            table.addCell(headerCell);

            headerCell = new PdfPCell(new Paragraph("笔试成绩(20%)", headerFont));
            table.addCell(headerCell);

            headerCell = new PdfPCell(new Paragraph("案例研讨成绩(40%)", headerFont));
            table.addCell(headerCell);

            headerCell = new PdfPCell(new Paragraph("谈心谈话成绩(40%)", headerFont));
            table.addCell(headerCell);

            headerCell = new PdfPCell(new Paragraph("最终分数", headerFont));
            table.addCell(headerCell);
            int index = 0;
            for(Scores scores:list){
                table.addCell(new Paragraph(name.get(index),font));
                table.addCell(new Paragraph(scores.getGrouping(),font));
                table.addCell(new Paragraph(scores.getDrawNumber(),font));
                table.addCell(new Paragraph(String.valueOf(scores.getWrittenTestScore()),font));
                table.addCell(new Paragraph(String.valueOf(scores.getDiscussionScore()),font));
                table.addCell(new Paragraph(String.valueOf(scores.getInterviewScore()),font));
                table.addCell(new Paragraph(String.valueOf(scores.getFinalScore()),font));
                index++;
            }
            document.add(table);
            document.close();
            writer.close();
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        return R.ok();
    }


    /**
     * 测试鉴权
     */
//    @SaCheckRole("admin")
//    @RequestMapping("/add")
//    public R add() {
//        return R.ok();
//    }
}
