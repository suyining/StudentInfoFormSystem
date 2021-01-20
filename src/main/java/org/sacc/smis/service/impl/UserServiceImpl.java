package org.sacc.smis.service.impl;

import org.sacc.smis.entity.User;
import org.sacc.smis.entity.UserRegisterParam;
import org.sacc.smis.mapper.UserMapper;
import org.sacc.smis.model.UserInfo;
import org.sacc.smis.service.UserService;
import org.sacc.smis.util.GetNullPropertyNamesUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by 林夕
 * Date 2021/1/19 22:02
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userMapper.findByStudentId(s);
        if(user == null){
            throw new UsernameNotFoundException(s);
        }
        return new UserInfo(user);
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public boolean register(UserRegisterParam userRegisterParam) {
        User user = new User();
        BeanUtils.copyProperties(userRegisterParam,user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.save(user);
        return true;
    }

    @Override
    public boolean updateInfo(User user) {
        User u = userMapper.findByPrimaryKey(user.getId());
        BeanUtils.copyProperties(user,u,GetNullPropertyNamesUtil.getNullPropertyNames(user));
        userMapper.save(u);
        return true;
    }
}
