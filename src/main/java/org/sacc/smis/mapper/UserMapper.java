package org.sacc.smis.mapper;

import org.sacc.smis.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by 林夕
 * Date 2021/1/19 20:15
 */
public interface UserMapper extends JpaRepository<User,Integer> {
    User findByStudentId(String studentId);

    @Query("select u from User u where u.id = :id")
    User findByPrimaryKey(@Param("id") Integer id);
}
