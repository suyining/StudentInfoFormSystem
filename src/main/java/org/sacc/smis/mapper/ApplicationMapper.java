package org.sacc.smis.mapper;

import org.sacc.smis.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationMapper extends JpaRepository<Application,Integer> {
}
