package com.yyq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yyq.pojo.dto.UserLoginDTO;
import com.yyq.pojo.entity.User;
import com.yyq.pojo.vo.UserStatVO;
import jakarta.servlet.http.HttpServletRequest;

public interface IUserService extends IService<User> {

    /**
     * 用户登录
     * @param userLoginDTO 登录参数
     * @return 用户实体
     */
    User login(UserLoginDTO userLoginDTO, HttpServletRequest request);
    /**
     * 手机号登录
     * @param userLoginDTO 登录参数
     * @return 用户实体
     */
    User loginByPhone(UserLoginDTO userLoginDTO, HttpServletRequest request);

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 根据手机号查询用户
     * @param phone
     * @return
     */
    User findByPhone(String phone);

    /**
     * 用户注册
     * @param userLoginDTO
     */
    void save(UserLoginDTO userLoginDTO, HttpServletRequest request);
    /**
     * 用户修改信息
     * @param userLoginDTO
     */
    void update(UserLoginDTO userLoginDTO);

    UserStatVO getUserStats(Long userId);
}
