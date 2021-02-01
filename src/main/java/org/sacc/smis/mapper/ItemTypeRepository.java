package org.sacc.smis.mapper;

import org.sacc.smis.entity.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 林夕
 * Date 2021/1/22 21:12
 */
public interface ItemTypeRepository extends JpaRepository<ItemType,Integer> {
}
