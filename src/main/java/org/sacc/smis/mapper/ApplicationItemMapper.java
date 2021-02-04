package org.sacc.smis.mapper;

import org.sacc.smis.entity.ApplicationItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationItemMapper extends JpaRepository<ApplicationItem, Integer> {
}
