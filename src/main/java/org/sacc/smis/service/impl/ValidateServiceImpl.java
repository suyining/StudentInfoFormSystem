package org.sacc.smis.service.impl;

import org.sacc.smis.service.ValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class ValidateServiceImpl implements ValidateService {
    @Autowired
    private JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String from;
    @Override
    public void sendPasswordResetEmail(SimpleMailMessage email) {

    }
}
