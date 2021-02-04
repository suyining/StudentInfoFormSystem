package org.sacc.smis.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by 林夕
 * Date 2021/2/1 18:11
 */
@Data
@Entity
public class Grades {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer userId;

    /**
     * 某科成绩名称，如：竞赛成绩，体育成绩，体测成绩
     */
    @Column(nullable = false)
    private String gradesName;

    /**
     * 具体的分数
     */
    @Column(nullable = false)
    private Integer grade;
}
