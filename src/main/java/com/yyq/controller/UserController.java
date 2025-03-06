package com.yyq.controller;

import com.yyq.common.constant.JwtClaimsConstant;
import com.yyq.common.constant.MessageConstant;
import com.yyq.common.properties.JwtProperties;
import com.yyq.common.result.Result;
import com.yyq.common.utils.JwtUtil;
import com.yyq.pojo.dto.UserLoginDTO;
import com.yyq.pojo.entity.User;
import com.yyq.pojo.vo.UserLoginVO;
import com.yyq.service.IUserService;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户管理
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO, HttpServletRequest request) {
        log.info("用户登录：{}", userLoginDTO);
        // 打印验证码验证情况
        log.info("用户输入验证码: {}", userLoginDTO.getVerificationCode());

        User user = userService.login(userLoginDTO, request);


        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .token(token)
                .avatar(user.getAvatar())
                .build();

        return Result.success(userLoginVO);
    }
    /**
     * 手机号验证码登录
     *
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/phoneLogin")
    public Result<UserLoginVO> phoneLogin(@RequestBody UserLoginDTO userLoginDTO, HttpServletRequest request) {
        log.info("手机号验证码登录：{}", userLoginDTO);
        // 打印验证码验证情况
        log.info("用户输入的手机验证码: {}", userLoginDTO.getPhoneCode());
        User user = userService.loginByPhone(userLoginDTO,request);

        // 登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .token(token)
                .avatar(user.getAvatar())
                .build();

        return Result.success(userLoginVO);
    }

    /**
     * 退出
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }
    /**
     * 注册
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/register")
    public Result<String> register(@RequestBody UserLoginDTO userLoginDTO, HttpServletRequest request) {
        log.info("用户注册：{}", userLoginDTO);
        userService.save(userLoginDTO, request);
        return Result.success("注册成功");
    }
    /**
     * 根据id查询用户
     * @param id
     * @return
     */
     @GetMapping("/{id}")
     public Result<User> getById(@PathVariable Long id){
         log.info("根据id查询用户：{}",id);
         User user =  userService.getById(id);
         return Result.success(user);
     }
    /**
     * 根据用户名查询用户
     * @param username
     */
    @GetMapping("/byUsername")
    public Result<User> getByUsername(@RequestParam String username) {
        log.info("根据用户名查询用户：{}", username);
        User user = userService.findByUsername(username);
        return user != null ? Result.success(user) : Result.error(MessageConstant.ACCOUNT_NOT_FOUND);
    }
    /**
     * 根据手机号查询用户
     * @param phone
     */
    @GetMapping("/byPhone")
    public Result<User> getByPhone(@RequestParam String phone) {
        log.info("根据手机号查询用户：{}", phone);
        User user = userService.findByPhone(phone);
        return user != null ? Result.success(user) : Result.error(MessageConstant.PHONE_NOT_FOUND);
    }
    /**
     * 修改用户信息
     * @param userLoginDTO
     */
    @PutMapping("/update")
    public Result<String> update(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("修改用户信息:{}", userLoginDTO);
        userService.update(userLoginDTO);
        return Result.success("用户信息更新成功");
    }
    /**
     * 分页查询用户
     * @param employeePageQueryDTO
     * @return
     */
/*     @GetMapping("/page")
     @ApiOperation(value = "分页查询员工")
     public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO){
         log.info("分页查询员工：{}",employeePageQueryDTO);
         PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);
         return Result.success(pageResult);
     }*/

     /**
      * 启用禁用用户账号
      * @param status
      * @param id
      * @return
      */
     /*@PostMapping("/status/{status}")
     public Result startOrStop(@PathVariable Integer status,Long id){
         log.info("启用禁用用户账号：{},{}",status,id);
         employeeService.startOrStop(status,id);
         return Result.success();
     }*/

    /**
     * 修改用户信息
     * @param userDTO
     */
/*    @PutMapping
    public Result update(@RequestBody UserDTO userDTO){
        log.info("更新用户信息:{}",userDTO);
        userService.update(userDTO);
        return Result.success();
    }*/

}
