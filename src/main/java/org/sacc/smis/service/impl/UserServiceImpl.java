package org.sacc.smis.service.impl;

import org.sacc.smis.entity.UpdatePassword;
import cn.hutool.core.bean.BeanUtil;
import org.sacc.smis.entity.User;
import org.sacc.smis.entity.UserRegisterParam;
import org.sacc.smis.entity.UserValidate;
import org.sacc.smis.enums.Business;
import org.sacc.smis.exception.BusinessException;
import org.sacc.smis.mapper.UserRepository;
import org.sacc.smis.model.RestResult;
import org.sacc.smis.model.UserInfo;
import org.sacc.smis.service.UserService;
import org.sacc.smis.service.ValidateService;
import org.sacc.smis.util.GetNullPropertyNamesUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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

    @Autowired
    private ValidateService validateService;

    @Value(value = "${spring.mail.username}")
    private String from;

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
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
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
    public boolean updatePassword(UpdatePassword u, Integer userId) {
        User user = userRepository.findByPrimaryKey(userId);
        if (u.getNewPassword().equals(u.getOldPassword()))
            throw new BusinessException(Business.OLD_PASSWORD_EQUAL_NEW_PASSWORD);
        else if (!passwordEncoder.matches(u.getOldPassword(), user.getPassword()))
            throw new BusinessException(Business.OLD_PASSWORD_ERROR);
        userRepository.updatePassword(userId, passwordEncoder.encode(u.getNewPassword()));
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

    @Override
    public Integer sendValidationEmail(String email, HttpServletRequest request) {
        User user = userRepository.findByEmail(email);
        if (user == null) throw new BusinessException(Business.USER_IS_NOT_EXIST);
        long status = validateService.sendValidateLimitation(email, 5, 5);
        switch ((int) status) {
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
                break;
            case -1:
                throw new BusinessException(Business.THE_NUMBER_OF_EMAILS_SENT_TODAY_HAS_REACHED_THE_LIMIT);
            default:
                return (int) status;

        }
        return 0;
    }
}
