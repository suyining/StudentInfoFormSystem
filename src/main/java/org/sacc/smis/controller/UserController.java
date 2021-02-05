package org.sacc.smis.controller;

import org.sacc.smis.entity.UpdatePassword;
import io.swagger.annotations.ApiParam;
import org.sacc.smis.entity.User;
import org.sacc.smis.entity.UserRegisterParam;
import org.sacc.smis.entity.UserValidate;
import org.sacc.smis.model.RestResult;
import org.sacc.smis.model.UserInfo;
import org.sacc.smis.service.UserService;
import org.sacc.smis.service.ValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by 林夕
 * Date 2021/1/19 20:19
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ValidateService validateService;

    @Value(value = "${spring.mail.username}")
    private String from;

    @ResponseBody
    @PostMapping("/register")
    public RestResult<Boolean> register(@RequestBody UserRegisterParam userRegisterParam){
        return RestResult.success(userService.register(userRegisterParam));
    }

    @ResponseBody
    @GetMapping("/findAll")
    public RestResult<List<User>> findAll(){
        return RestResult.success(userService.findAll());
    }

    @ResponseBody
    @PostMapping("/update")
    /**
     * Authentication authentication 从session中拿到用户信息
     */
    public RestResult<Boolean> update(@RequestBody User user, Authentication authentication){
        UserInfo userInfo = (UserInfo)authentication.getPrincipal();
        user.setId(userInfo.getId());
        return RestResult.success(userService.updateInfo(user));
    }

    /**
     * 发送忘记密码邮件请求，每日申请次数不超过5次，每次申请间隔不低于 5分钟
     * @param email
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/validate/sendValidationEmail")
    //通过邮箱找回密码
    public RestResult<String> sendValidationEmail(@ApiParam("邮箱地址") @RequestParam("email") String email,
                                                  HttpServletRequest request) {
        User user = userService.findUserByEmail(email);
        RestResult<String> responseBody;
        if (user == null) {
            return RestResult.error(404, "该用户不存在");
        } else {
            long status = validateService.sendValidateLimitation(email, 5, 5);
            switch ((int) status){
                case 0:
                    //插入一行数据,带有Token
                    UserValidate userValidate = new UserValidate();
                    validateService.insertNewResetRecord(userValidate, user, UUID.randomUUID().toString());
                    //设置邮件内容
                    String appUrl = request.getScheme() + "://" + request.getServerName();
                    SimpleMailMessage passWordResetEmail = new SimpleMailMessage();
                    passWordResetEmail.setFrom(from);
                    passWordResetEmail.setTo(email);
                    passWordResetEmail.setSubject("【学生表单管理系统】忘记密码");
                    passWordResetEmail.setText("您正在申请重置密码，请点击此链接重置密码: \n" +
                            appUrl + ":8080/validate/resetPassword?token=" + userValidate.getResetToken());
                    validateService.sendPasswordResetEmail(passWordResetEmail);
                    responseBody = RestResult.success(400, "发送完成");
                    break;
                case -1:
                    responseBody = RestResult.error( "今日重置密码已经超过上限!");
                    break;
                default:
                    responseBody = RestResult.error( "操作过于频繁,请在"+ status + "秒后再试!");
            }
        }
        return responseBody;
    }

    /**
     * 将url的token和数据库里的token匹配，成功后便可修改密码，token有效期为60分钟
     *
     * @param token
     * @param password
     * @param confirmPassword
     * @return
     */
    @ResponseBody
    @PostMapping("/validate/resetPassword")
    public RestResult<String> resetPassword(@ApiParam("Token") @RequestParam("token") String token,
                                            @ApiParam("密码") @RequestParam("password") String password,
                                            @ApiParam("密码确认") @RequestParam("confirmPassword") String confirmPassword) {
        RestResult<String> responseBody;
        List<UserValidate> validates = validateService.findUserByResetToken(token);
        if (validates == null) {
            responseBody = RestResult.error(400, "This reset password request is not exist");
        } else {
            UserValidate userValidate = validates.get(0);
            if (validateService.validateLimitation(userValidate.getEmail(), Long.MAX_VALUE, 60, token)) {
                Integer userId = userValidate.getUserId();
                if (password.equals(confirmPassword)) {
                    if (userService.updatePassword(userId, password)) {
                        responseBody = RestResult.success(400, "修改完成");
                    } else {
                        responseBody = RestResult.error(500, "修改失败");
                    }
                } else {
                    responseBody = RestResult.error("确认密码和密码不一致，请重新输入");
                }
            } else {
                responseBody = RestResult.error("该链接失效");
            }
        }
        return responseBody;
    }


    @ResponseBody
    @GetMapping("/userInfo")
    @PreAuthorize("hasRole('STUDENT')")
    public RestResult<User> getUserInfo(Authentication authentication){
        UserInfo userInfo = (UserInfo)authentication.getPrincipal();
        // 隐藏密码等敏感信息
        userInfo.setPassword("n/a");
        return RestResult.success(userInfo);
    }

    @ResponseBody
    @PostMapping("/updatePassword")
    public RestResult<Boolean> updatePassword(@RequestBody @Validated UpdatePassword updatePassword,
                                              BindingResult bindingResult,
                                              Authentication authentication){
        if(bindingResult.hasErrors()){
            return RestResult.error(-1, Objects.requireNonNull(bindingResult.getFieldError()).getField()+
                    bindingResult.getFieldError().getDefaultMessage());
        }
        UserInfo userInfo = (UserInfo)authentication.getPrincipal();
        return RestResult.success(userService.updatePassword(updatePassword,userInfo.getId()));
    }
}
