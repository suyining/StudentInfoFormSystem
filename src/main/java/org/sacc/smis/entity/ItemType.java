package org.sacc.smis.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class ItemType {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 类型名称，比如说文本类型/预制类型/选择类型
     */
    @Column(nullable = false)
    private String name;
    /**
     * 预制或选择的数据来源
     */
    private String dataSource;
    /**
     * 校验规则
     */
    private String validRule;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
