package com.yyq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyq.mapper.UserFollowMapper;
import com.yyq.mapper.UserMapper;
import com.yyq.pojo.entity.User;
import com.yyq.pojo.entity.UserFollow;
import com.yyq.service.IUserFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserFollowServiceImpl extends ServiceImpl<UserFollowMapper, UserFollow> implements IUserFollowService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public void removeByUserIds(Long userId, Long followedUserId) {
        baseMapper.removeByUserIds(userId, followedUserId);
    }

    /**
     * 查询关注列表
     * @param userId
     * @return
     */
    @Override
    public List<User> getFollowingList(Long userId) {
        // 1.查询用户关注关系表
        LambdaQueryWrapper<UserFollow> lqw = new LambdaQueryWrapper<>();
        lqw.select(UserFollow::getFolloweeId) // 只查询被关注者ID列
                .eq(UserFollow::getUserId, userId);
        List<Long> followedUserIds = list(lqw).stream()
                .map(UserFollow::getFolloweeId)
                .collect(Collectors.toList());

        // 2.批量查询用户信息
        if (followedUserIds.isEmpty()) {
            return Collections.emptyList();
        }
        return userMapper.selectBatchIds(followedUserIds);
    }
    /**
     * 查询粉丝列表
     */
    @Override
    public List<User> getFollowerList(Long userId) {
        // 1.查询关注当前用户的记录
        LambdaQueryWrapper<UserFollow> lqw = new LambdaQueryWrapper<>();
        lqw.select(UserFollow::getUserId) // 只查询关注者ID列
                .eq(UserFollow::getFolloweeId, userId);
        List<Long> followerIds = list(lqw).stream()
                .map(UserFollow::getUserId)
                .collect(Collectors.toList());

        // 2.批量查询用户信息
        if (followerIds.isEmpty()) {
            return Collections.emptyList();
        }
        return userMapper.selectBatchIds(followerIds);
    }
    /**
     * 获取用户关注的用户ID列表
     */
    @Override
    public List<Long> getFolloweeIds(Long userId) {
        // 1.查询用户关注关系表
        LambdaQueryWrapper<UserFollow> lqw = new LambdaQueryWrapper<>();
        lqw.select(UserFollow::getFolloweeId) // 只查询被关注者ID列
                .eq(UserFollow::getUserId, userId);
        List<Long> followedUserIds = list(lqw).stream()
                .map(UserFollow::getFolloweeId)
                .collect(Collectors.toList());
        return followedUserIds;
    }

}
