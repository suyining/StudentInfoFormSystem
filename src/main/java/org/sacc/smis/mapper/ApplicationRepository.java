package org.sacc.smis.mapper;

import org.sacc.smis.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 林夕
 * Date 2021/1/22 21:13
 */
public interface ApplicationRepository extends JpaRepository<Application,Integer> {
}
