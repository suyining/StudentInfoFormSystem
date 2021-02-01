package org.sacc.smis.mapper;

import org.sacc.smis.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 林夕
 * Date 2021/1/22 20:44
 */
public interface ItemRepository extends JpaRepository<Item,Integer> {
}
