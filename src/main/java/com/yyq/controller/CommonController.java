package com.yyq.controller;


import com.yyq.common.constant.MessageConstant;
import com.yyq.common.result.Result;
import com.yyq.common.utils.AliOssUtil;
import com.yyq.common.utils.SmsUtil;
import com.yyq.common.utils.VerificationCodeUtil;
import com.yyq.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通用接口
 */
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传: {}",file);
        try {
            //原始名
            String originalFilename = file.getOriginalFilename();
            //截取原文件名后缀
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            //生成新的文件名
            String objectName= UUID.randomUUID().toString() + suffix;
            //返回文件请求路径
            String filePath = aliOssUtil.upload(file.getBytes(), objectName);
            return Result.success(filePath);
        } catch (IOException e) {
            log.error("文件上传失败,{}",e);
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }

    /**
     * 生成4位验证码
     * @return
     */
    @GetMapping("/verificationCode")
    public Result<String> generateVerificationCode(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String verificationCode = VerificationCodeUtil.generateVerificationCode();
        log.info("生成验证码: {}", verificationCode);
        log.info("验证码存入session: {}", session.getId());
        session.setAttribute("verificationCode", verificationCode);
        return Result.success(verificationCode);
    }

    /**
     * 发送短信验证码
     * @param phone 用户手机号
     * @return 结果
     */
    @GetMapping("/sendSmsCode")
    public Result<String> sendSmsCode(@RequestParam String phone, HttpServletRequest request) throws Exception {
        //获取6位随机验证码
        String phoneCode = SmsUtil.generatePhoneVerificationCode();
        // 调用 SmsUtil 发送验证码
        SmsUtil.sendMessage("阿里云短信测试", "SMS_154950909", phone, phoneCode);
        request.getSession().setAttribute("phoneCode",phoneCode);
        log.info("验证码存入session: {}", request.getSession().getId());
        log.info("生成的验证码: {}", phoneCode);
        return Result.success();
    }
}
