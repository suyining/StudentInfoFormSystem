package org.sacc.smis.service.impl;

import org.sacc.smis.entity.UpdatePassword;
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

import java.time.LocalDateTime;
import java.util.List;

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
        if(user == null){
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
        if(userRepository.findByStudentId(userRegisterParam.getStudentId())!=null)
            throw new BusinessException(Business.STUDENT_ID_IS_EXIT);
        else if(userRepository.findByEmail(userRegisterParam.getEmail())!=null)
            throw new BusinessException(Business.EMAIL_IS_EXIT);
        User user = new User();
        BeanUtils.copyProperties(userRegisterParam,user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean updateInfo(User user) {
        User u = userRepository.findByPrimaryKey(user.getId());
        BeanUtils.copyProperties(user,u,GetNullPropertyNamesUtil.getNullPropertyNames(user));
        userRepository.save(u);
        return true;
    }

    @Override
    public boolean updatePassword(UpdatePassword u, Integer userId) {
        User user = userRepository.findByPrimaryKey(userId);
        if(u.getNewPassword().equals(u.getOldPassword()))
            throw new BusinessException(Business.OLD_PASSWORD_EQUAL_NEW_PASSWORD);
        else if(!passwordEncoder.matches(u.getOldPassword(),user.getPassword()))
            throw new BusinessException(Business.OLD_PASSWORD_ERROR);
        userRepository.updatePassword(userId,passwordEncoder.encode(u.getNewPassword()));
        return true;
    }

    @Override
    public boolean alter(User user, String newPassword){
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }

}
