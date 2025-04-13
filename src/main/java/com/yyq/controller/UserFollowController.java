package com.yyq.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yyq.common.result.Result;
import com.yyq.pojo.entity.User;
import com.yyq.pojo.entity.UserFollow;
import com.yyq.service.IUserFollowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/follow")
@Slf4j
public class UserFollowController {
    @Autowired
    private IUserFollowService  userFollowService;
    // 新增关注
    @PostMapping
    public Result<String> addFollow(@RequestBody UserFollow userFollow) {
        log.info("添加关注：{}",userFollow);
        userFollow.setCreateTime(LocalDateTime.now());
        userFollowService.save(userFollow);
        return Result.success("添加成功");
    }
    // 取消关注
    @DeleteMapping
    public Result<String> cancelFollow(@RequestParam Long userId,@RequestParam Long followeeId) {
        log.info("取消关注：{},{}",userId,followeeId);
        userFollowService.removeByUserIds(userId, followeeId);
        return Result.success("取消关注成功");
    }
    // 查询关注列表
    @GetMapping("/following/{userId}")
    public Result<List<User>> getFollowingList(@PathVariable Long userId) {
        log.info("查询关注列表：{}",userId);
        List<User> list = userFollowService.getFollowingList(userId);
        return Result.success(list);
    }
    // 查询粉丝列表
    @GetMapping("/fans/{userId}")
    public Result<List<User>> getFollowerList(@PathVariable Long userId) {
        log.info("查询粉丝列表：{}",userId);
        List<User> list = userFollowService.getFollowerList(userId);
        return Result.success(list);
    }
    /**
     * 查询单条记录
     */
    @GetMapping("/status")
    public Result<Boolean> getFollowStatus(@RequestParam Long userId, @RequestParam Long followeeId) {
        log.info("查询关注状态：{},{}",userId,followeeId);
        LambdaQueryWrapper<UserFollow> lqw = new LambdaQueryWrapper<>();
        lqw.eq(UserFollow::getUserId, userId)
                .eq(UserFollow::getFolloweeId, followeeId);
        UserFollow userFollow = userFollowService.getOne(lqw);
        if (userFollow != null) {
            return Result.success(true);
        }
        return Result.success(false);
    }
}
