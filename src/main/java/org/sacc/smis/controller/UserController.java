package org.sacc.smis.controller;

import org.sacc.smis.entity.UpdatePassword;
import org.sacc.smis.entity.User;
import org.sacc.smis.entity.UserRegisterParam;
import org.sacc.smis.model.RestResult;
import org.sacc.smis.model.UserInfo;
import org.sacc.smis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * Created by 林夕
 * Date 2021/1/19 20:19
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

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

    @ResponseBody
    @PostMapping("/alter")
    public RestResult<Boolean> alter(@RequestBody User user, String newPassword){
        return RestResult.success(userService.alter(user, newPassword));
    }

}
