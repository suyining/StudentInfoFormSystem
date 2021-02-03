package org.sacc.smis.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/*
* 用以验证用户身份来
* */
@Entity
public class UserValidate {
    @Id
    @GeneratedValue
    private Integer id;

    /*
    * 用户ID
    * */
    @Column(nullable = false)
    private Integer userId;

    /*
    * 用户Email
    * */
    @Column(nullable = false)
    private Integer email;

    /*
    *用户Token
    */
    @Column(nullable = false)
    private Integer resetToken;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
