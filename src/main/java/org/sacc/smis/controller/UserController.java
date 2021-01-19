package org.sacc.smis.controller;

import org.sacc.smis.entity.User;
import org.sacc.smis.entity.UserRegisterParam;
import org.sacc.smis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ResponseBody
    @PostMapping("/register")
    public User register(@RequestBody UserRegisterParam userRegisterParam){
        User user = new User();
        user.setStudentId(userRegisterParam.getStudentId());
        user.setPassword(passwordEncoder.encode(userRegisterParam.getPassword()));
        user.setRole("1");
        user.setEmail("22233@qq.com");
        return userMapper.save(user);
    }
    @ResponseBody
    @GetMapping("/findAll")
    public List<User> findAll(){
        return userMapper.findAll();
    }
}
