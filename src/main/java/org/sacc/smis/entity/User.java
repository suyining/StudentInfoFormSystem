package org.sacc.smis.entity;

import lombok.Data;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.config.authentication.PasswordEncoderParser;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(
        indexes = {
                @Index(name = "email", columnList = "email",unique = true),
                @Index(name = "studentId", columnList = "studentId", unique = true)
        }
)
@EntityListeners(AuditingEntityListener.class)
public class User {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String studentId;
    @Column(nullable = false)
    private String role = "0";
    /**
     * 学院
     */
    //@Column(nullable = false)
    private String college;
    /**
     * 年级
     */
    //@Column(nullable = false)
    private Integer grade;
    /**
     * 专业
     */
    //@Column(nullable = false)
    private String profession;
    /**
     * 身份证
     */
    //@Column(nullable = false)
    private String idCord;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
