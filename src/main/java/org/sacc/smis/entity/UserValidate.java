package org.sacc.smis.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

/*
* 用以验证用户身份来
* */
@Entity
@Data
@Table(indexes = {
        @Index(name = "user_id" ,columnList = "userId", unique = true),
        @Index(name ="user_email" ,columnList = "email" ,unique = true)
})
public class UserValidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String email;

    /*
    *用户Token
    */
    @Column(nullable = false)
    private String resetToken;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
