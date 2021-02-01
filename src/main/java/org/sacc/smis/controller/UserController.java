package org.sacc.smis.controller;

import org.sacc.smis.entity.User;
import org.sacc.smis.entity.UserRegisterParam;
import org.sacc.smis.model.RestResult;
import org.sacc.smis.model.UserInfo;
import org.sacc.smis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
}
