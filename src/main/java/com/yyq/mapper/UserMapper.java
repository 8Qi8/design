package com.yyq.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyq.pojo.entity.User;
import com.yyq.pojo.vo.UserStatVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> { // 继承BaseMapper
    // 保留自定义方法
    default User getByUsername(String username) {
        return selectOne(new QueryWrapper<User>().eq("username", username));
    }
    // 根据id查询用户名
    @Select("select username from user where id = #{userId}")
    String findUserNameById(Long userId);
    // 根据id查询用户头像
    @Select("select avatar from user where id = #{userId}")
    String findUserAvatarById(Long userId);

    UserStatVO getUserStats(Long userId);
}
