package com.yyq.controller;

import com.yyq.common.result.Result;
import com.yyq.pojo.entity.ActivitySignup;
import com.yyq.pojo.vo.SignupVO;
import com.yyq.service.IActivitySignupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/activitySignup")
public class ActivitySignupController {
    @Autowired
    private IActivitySignupService activitySignupService;
    // 报名活动
    @PostMapping
    public Result<String> signup(@RequestBody ActivitySignup signup) {
        signup.setSignupTime(LocalDateTime.now());
        signup.setStatus("待审核"); // 默认待审核
        activitySignupService.save(signup);
        return Result.success("报名成功，等待审核");
    }
    // 删除报名记录
    @DeleteMapping("/{id}")
    public Result<String> cancelSignup(@PathVariable Long id) {
        activitySignupService.removeById(id);
        return Result.success("已取消报名");
    }

    //查询某个用户所有记录
    @GetMapping("/{userId}")
    public Result<List<ActivitySignup>> getSignupByUserId(@PathVariable Long userId) {
        List<ActivitySignup> signup = activitySignupService.getSignupByUserId(userId);
        return Result.success(signup);
    }
    // 用户修改报名信息
    @PutMapping("/update")
    public Result<String> update(@RequestBody ActivitySignup signup) {
        activitySignupService.updateById(signup);
        return Result.success("修改成功");
    }

    // 审核报名信息
    @PutMapping
    public Result<String> review(@RequestBody ActivitySignup signup) {
        signup.setFinishTime(LocalDateTime.now());
        activitySignupService.updateById(signup);
        return Result.success("审核成功");
    }

    //查询所有报名记录
    @GetMapping
    public Result<List<SignupVO>> getAllSignup() {
        List<SignupVO> signup = activitySignupService.getAllSignup();
        return Result.success(signup);
    }
    //  查询某个活动所有报名记录
    @GetMapping("/activity/{activityId}")
    public Result<List<ActivitySignup>> getSignupByActivityId(@PathVariable Long activityId) {
        List<ActivitySignup> signup = activitySignupService.lambdaQuery().eq(ActivitySignup::getActivityId, activityId).list();
        return Result.success(signup);
    }
    // 查询用户是否报名
    @GetMapping("/user/{userId}/{activityId}")
    public Result<Boolean> isSignup(@PathVariable Long userId, @PathVariable Long activityId) {
        ActivitySignup signup = activitySignupService.lambdaQuery().eq(ActivitySignup::getUserId, userId).eq(ActivitySignup::getActivityId, activityId).one();
        return Result.success(signup != null);
    }


}
