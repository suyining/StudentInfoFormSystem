package org.sacc.smis.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class ItemValue {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 对应的Item
     */
    @Column(nullable = false)
    private Integer itemId;
    /**
     * 填写人
     */
    @Column(nullable = false)
    private Integer userId;

    private String value;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
