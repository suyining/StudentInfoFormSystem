package org.sacc.smis.service.impl;

import cn.hutool.core.bean.BeanUtil;
import org.sacc.smis.entity.User;
import org.sacc.smis.entity.UserRegisterParam;
import org.sacc.smis.enums.Business;
import org.sacc.smis.exception.BusinessException;
import org.sacc.smis.mapper.UserRepository;
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
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByStudentId(s);
        if (user == null) {
            throw new UsernameNotFoundException(s);
        }
        return new UserInfo(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean register(UserRegisterParam userRegisterParam) {
        if (userRepository.findByStudentId(userRegisterParam.getStudentId()) != null)
            throw new BusinessException(Business.STUDENT_ID_IS_EXIT);
        else if (userRepository.findByEmail(userRegisterParam.getEmail()) != null)
            throw new BusinessException(Business.EMAIL_IS_EXIT);
        User user = new User();
        BeanUtils.copyProperties(userRegisterParam, user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean updateInfo(User user) {
        User u = userRepository.findByPrimaryKey(user.getId());
        BeanUtils.copyProperties(user, u, GetNullPropertyNamesUtil.getNullPropertyNames(user));
        userRepository.save(u);
        return true;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserByStudentId(String studentId) {
        return userRepository.findByStudentId(studentId);
    }

    @Override
    public boolean updatePassword(Integer userId, String password) {
        User user = userRepository.findByPrimaryKey(userId);
        user.setId(userId);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return true;
    }


}
