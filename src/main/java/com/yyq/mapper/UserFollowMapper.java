package com.yyq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyq.pojo.entity.UserFollow;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserFollowMapper extends BaseMapper<UserFollow> {
    @Delete("delete from user_follow where user_id = #{userId} and followee_id = #{followeeId}")
    void removeByUserIds(Long userId, Long followeeId);
}
