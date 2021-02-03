package org.sacc.smis.service;

import org.springframework.mail.SimpleMailMessage;

public interface ValidateService {

    void sendPasswordResetEmail(SimpleMailMessage email);


}
