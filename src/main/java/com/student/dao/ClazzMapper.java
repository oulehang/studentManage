package com.student.dao;

import com.student.bean.Clazz;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClazzMapper {

    int insert(Clazz clazz);
    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(Clazz record);

    List<Clazz> selectByGradeId(int GradeId);

    List<Clazz> getAllClazz();

    List<Clazz> getClazzBy(Clazz clazz);

    Clazz selectByPrimaryKey(Integer clazzid);

    Clazz selectByName(@Param("name") String name,@Param("gradeid") int gradeid);
}