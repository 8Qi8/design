package com.yyq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yyq.pojo.entity.ActivitySignup;
import com.yyq.pojo.vo.SignupVO;

import java.util.List;

public interface IActivitySignupService extends IService<ActivitySignup> {

    List<ActivitySignup> getSignupByUserId(Long userId);

    List<SignupVO> getAllSignup();
}
