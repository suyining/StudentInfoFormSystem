package org.sacc.smis.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 表单实体
 */
@Entity
@Data
public class Application {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 表单名称
     */
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer userId;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
