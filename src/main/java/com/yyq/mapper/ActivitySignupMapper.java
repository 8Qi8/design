package com.yyq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyq.pojo.entity.ActivitySignup;
import com.yyq.pojo.vo.SignupVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ActivitySignupMapper extends BaseMapper<ActivitySignup> {
    List<SignupVO> selectAllSignup();
}
