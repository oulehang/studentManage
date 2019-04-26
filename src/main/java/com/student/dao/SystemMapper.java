package com.student.dao;

import com.student.bean.System;
import org.apache.ibatis.annotations.Param;

public interface SystemMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(System record);


    System selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(System record);

    int updateByPrimaryKey(System record);

    System selectAll();


    int editSystem(@Param("id") String id,@Param("name") String name,@Param("value") String value);
}