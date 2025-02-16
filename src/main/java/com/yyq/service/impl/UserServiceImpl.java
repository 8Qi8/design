package com.yyq.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyq.common.constant.MessageConstant;
import com.yyq.common.exception.AccountNotFoundException;
import com.yyq.common.exception.PasswordErrorException;
import com.yyq.common.exception.VerificationCodeErrorException;
import com.yyq.mapper.UserMapper;
import com.yyq.pojo.dto.UserLoginDTO;
import com.yyq.pojo.entity.User;
import com.yyq.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;


@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    /**
     * 用户登录
     * @param userLoginDTO
     * @param request
     * @return
     */
    @Override
    public User login(UserLoginDTO userLoginDTO, HttpServletRequest request) {
        // 使用LambdaQueryWrapper
        User user = lambdaQuery()
                .eq(User::getUsername, userLoginDTO.getUsername())
                .one();

        if (user == null) {
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        String encryptedPwd = DigestUtils.md5DigestAsHex(userLoginDTO.getPassword().getBytes());
        if (!encryptedPwd.equals(user.getPassword())) {
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        HttpSession session = request.getSession();
        // 验证验证码
        String storedVerificationCode = (String)session.getAttribute("verificationCode");
        log.info("sessionId:{}", session.getId());
        log.info("存储验证码: {}", storedVerificationCode);
        if (storedVerificationCode == null || !storedVerificationCode.equalsIgnoreCase(userLoginDTO.getVerificationCode())) {
            throw new VerificationCodeErrorException(MessageConstant.VERIFICATION_CODE_ERROR);
        }

        return user;
    }
    /**
     * 手机号登录
     * @param userLoginDTO
     * @return
     */
    @Override
    public User loginByPhone(UserLoginDTO userLoginDTO,HttpServletRequest request) {
        HttpSession session = request.getSession();

        // 使用LambdaQueryWrapper
        //
        User user = lambdaQuery()
                .eq(User::getPhone, userLoginDTO.getPhone())
                .one();

        if (user == null) {
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }


        // 验证手机验证码
        String storedPhoneCode = (String) session.getAttribute("phoneCode");
        log.info("sessionId:{}", session.getId());
        log.info("存储手机验证码: {}", storedPhoneCode);
        if (storedPhoneCode == null || !storedPhoneCode.equals(userLoginDTO.getPhoneCode())) {
            throw new VerificationCodeErrorException(MessageConstant.VERIFICATION_CODE_ERROR);
        }
        session.removeAttribute("phoneCode");
        return user;
    }
    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        return this.lambdaQuery()
                .eq(User::getUsername, username)
                .one(); // 返回单个结果
    }

    /**
     * 根据手机号查询用户
     */
    public User findByPhone(String phone) {
        return this.lambdaQuery()
                .eq(User::getPhone, phone)
                .one();
    }
    /**
     * 保存用户
     * @param userLoginDTO
     */
    @Override
    public void save(UserLoginDTO userLoginDTO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        //判断验证码是否相等
        String phoneCode = (String) session.getAttribute("phoneCode");
        if (!phoneCode.equals(userLoginDTO.getPhoneCode())) {//验证码错误
            throw new VerificationCodeErrorException(MessageConstant.VERIFICATION_CODE_ERROR);
        }
        session.removeAttribute("phoneCode");
        User user = new User();
        user.setUsername(userLoginDTO.getUsername());
        //设置默认头像
        user.setAvatar("https://yyqnews.oss-cn-beijing.aliyuncs.com/default-avatar.png");
        user.setPassword(DigestUtils.md5DigestAsHex(userLoginDTO.getPassword().getBytes()));
        user.setPhone(userLoginDTO.getPhone());
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        this.save(user);
    }
    /**
     * 动态更新用户
     * @param userLoginDTO
     */
    @Override
    public void update(UserLoginDTO userLoginDTO) {
        log.info("更新用户信息：{}", userLoginDTO);

        // 获取当前用户信息
        User currentUser = lambdaQuery()
                .eq(User::getUsername, userLoginDTO.getUsername())
                .one();
        // 动态更新字段
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getUsername, userLoginDTO.getUsername());

        if (userLoginDTO.getPassword() != null && !userLoginDTO.getPassword().isEmpty()) {
            updateWrapper.set(User::getPassword, DigestUtils.md5DigestAsHex(userLoginDTO.getPassword().getBytes()));
        }
        if (userLoginDTO.getPhone() != null && !userLoginDTO.getPhone().isEmpty()) {
            updateWrapper.set(User::getPhone, userLoginDTO.getPhone());
        }
        if (userLoginDTO.getDescription() != null && !userLoginDTO.getDescription().isEmpty()) {
            updateWrapper.set(User::getDescription, userLoginDTO.getDescription());
        }
        if (userLoginDTO.getAvatar() != null && !userLoginDTO.getAvatar().isEmpty()) {
            updateWrapper.set(User::getAvatar, userLoginDTO.getAvatar());
        }
        if (userLoginDTO.getSex() != null) {
            updateWrapper.set(User::getSex, userLoginDTO.getSex());
        }
        updateWrapper.set(User::getUpdateTime, LocalDateTime.now());

        // 执行更新操作
        this.update(updateWrapper);
    }

}
