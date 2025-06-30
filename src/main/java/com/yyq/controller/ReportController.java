package com.yyq.controller;

import com.yyq.common.result.Result;
import com.yyq.pojo.entity.Article;
import com.yyq.pojo.entity.Message;
import com.yyq.pojo.entity.Report;
import com.yyq.service.IArticleService;
import com.yyq.service.IMessageService;
import com.yyq.service.IReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private IReportService reportService;
    @Autowired
    private IMessageService messageService;
    @Autowired
    private IArticleService articleService;

    //新增举报记录
    @PostMapping
    public Result<String> reportArticle(@RequestBody Report report) {
        report.setReportTime(LocalDateTime.now());
        report.setStatus("未处理");
        reportService.save(report);
        return Result.success("举报成功，感谢您的反馈！");
    }
    // 处理举报
    @PutMapping
    public Result<String> handleReport(@RequestParam Long id,@RequestParam String articleTitle,@RequestParam Long userId, @RequestParam Long targetUserId ,@RequestParam  Long  adminId,@RequestParam String result) {
        Report report = reportService.getById(id);
        report.setStatus("已处理");
        report.setResult(result);
        //向举报者发送消息
        Message message = new Message();
        message.setSenderId(adminId);
        message.setReceiverId(userId);
        message.setContent("您举报的文章《"+articleTitle+"》已被处理，结果为：" + result);
        messageService.sendPrivateMessage(message);
        //向被举报着发送消息
        Message message1 = new Message();
        message1.setSenderId(adminId);
        message1.setReceiverId(targetUserId);
        message1.setContent("您的文章《"+articleTitle+"》被举报，处理结果为：" + result);
        messageService.sendPrivateMessage(message1);
        reportService.updateById(report);
        return Result.success("处理成功");
    }
    //查询举报记录
    @GetMapping("/{id}")
    public Result<Report> getReport(@PathVariable Long id) {
        Report report = reportService.getById(id);
        return Result.success(report);
    }
    // 查询所有举报记录
    @GetMapping
    public Result<List<Report>> getAllReports() {
        List<Report> reports = reportService.list();
        return Result.success(reports);
    }
}
