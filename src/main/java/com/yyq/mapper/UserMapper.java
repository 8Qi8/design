package com.yyq.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyq.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> { // 继承BaseMapper
    // 可保留自定义方法
    default User getByUsername(String username) {
        return selectOne(new QueryWrapper<User>().eq("username", username));
    }
}
