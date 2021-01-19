package org.sacc.smis.mapper;

import org.sacc.smis.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by 林夕
 * Date 2021/1/19 20:15
 */
public interface UserMapper extends JpaRepository<User,Integer> {

}
