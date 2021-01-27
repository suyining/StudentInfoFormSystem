package org.sacc.smis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.sacc.smis.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//Item表单Mapper
public interface ItemMapper extends JpaRepository<Item,Integer> {





}
