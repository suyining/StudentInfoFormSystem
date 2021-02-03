package org.sacc.smis.service.impl;

import org.sacc.smis.entity.User;
import org.sacc.smis.entity.UserValidate;
import org.sacc.smis.mapper.ValidateMapper;
import org.sacc.smis.service.ValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ValidateServiceImpl implements ValidateService {
    @Autowired
    private JavaMailSenderImpl sender;

    @Autowired
    private ValidateMapper validateMapper;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送邮件
     * @param email
     */
    @Override
    public void sendPasswordResetEmail(SimpleMailMessage email) {
        sender.send(email);
    }

    /**
     * 插入一条validate记录
     * @param validate
     * @param users
     * @param token
     * @return
     */
    @Override
    public UserValidate insertNewResetRecord(UserValidate validate, User users, String token) {
        validate.setId(users.getId());
        validate.setEmail(users.getEmail());
        validate.setResetToken(token);
        return validateMapper.save(validate);
    }

    /**
     * 通过Token查找申请记录
     * @param resetToken
     * @return
     */
    @Override
    public List<UserValidate> findUserByResetToken(String resetToken) {
        return validateMapper.findByToken(resetToken);
    }

    /**
     * 验证连接是否失效：链接有两种情况失效 1.超时 2.最近请求的一次链接自动覆盖之前的链接（待看代码）
     * @param email
     * @param requestPerDay
     * @param interval
     * @param token
     * @return
     * TODO:实现判断重置链接是否失效待完善
     */
    @Override
    public boolean validateLimitation(String email, long requestPerDay, long interval, String token) {
//        List<UserValidate> validates = validateMapper.findByEmail(email);
//        Optional validate = validates.stream().map(UserValidate::getUpdatedAt).max(Date::compareTo);
//        Date dateOfLastRequest = new Date();
//        if (validate.isPresent()) dateOfLastRequest = (Date) validate.get();
//        long intervalForLastRequest = new Date().getTime() - dateOfLastRequest.getTime();

        return true;
    }

    /**
     * 验证是否发送重置邮件：每个email的重置密码每日请求上限为requestPerDay次，与上一次的请求时间间隔为interval分钟。
     * @param email
     * @param requestPerDay
     * @param interval
     * @return
     * TODO:实现判断重置次数是否失效待完善
     */
    @Override
    public boolean sendValidateLimitation(String email, long requestPerDay, long interval) {
        List<UserValidate> validates = validateMapper.findByEmail(email);
        if (validates.isEmpty()){
            return true;
        }
//        long countTodayValidation = validates.stream().filter(x-> DateUtils.isSameDay(x.getGmtModified(), new Date())).count();
//        Optional validate = validates.stream().map(Validate::getGmtModified).max(Date::compareTo);
//        Date dateOfLastRequest = new Date();
//        if (validate.isPresent()) dateOfLastRequest = (Date) validate.get();
//        long intervalForLastRequest = new Date().getTime() - dateOfLastRequest.getTime();
//        return countTodayValidation <= requestPerDay && intervalForLastRequest >= interval * 60 * 1000;
        return true;
    }
}
