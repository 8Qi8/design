package com.yyq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yyq.pojo.entity.User;
import com.yyq.pojo.entity.UserFollow;

import java.util.List;

public interface IUserFollowService extends IService<UserFollow> {
    void removeByUserIds(Long userId, Long followedUserId);

    List<User> getFollowingList(Long userId);

    List<User> getFollowerList(Long userId);

    List<Long> getFolloweeIds(Long userId);
}
