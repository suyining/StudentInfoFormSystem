package org.sacc.smis.mapper;

import org.sacc.smis.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

//Item表单Mapper
public interface ItemRepository extends JpaRepository<Item, Integer> {


}
