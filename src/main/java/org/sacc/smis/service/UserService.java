package org.sacc.smis.service;

import org.sacc.smis.entity.User;
import org.sacc.smis.entity.UserRegisterParam;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 林夕
 * Date 2021/1/19 22:02
 */
public interface UserService {
    List<User> findAll();

    boolean register(UserRegisterParam userRegisterParam);

    boolean updateInfo(User user);
}
