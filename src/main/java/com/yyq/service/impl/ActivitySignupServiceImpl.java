package com.yyq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyq.mapper.ActivitySignupMapper;
import com.yyq.pojo.entity.ActivitySignup;
import com.yyq.pojo.vo.SignupVO;
import com.yyq.service.IActivitySignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivitySignupServiceImpl extends ServiceImpl<ActivitySignupMapper, ActivitySignup> implements IActivitySignupService {
    @Autowired
    private ActivitySignupMapper activitySignupMapper;

    @Override
    public List<ActivitySignup> getSignupByUserId(Long userId) {
        return this.list(new QueryWrapper<ActivitySignup>().eq("user_id", userId));
    }

    @Override
    public List<SignupVO> getAllSignup() {
        return activitySignupMapper.selectAllSignup();
    }
}
