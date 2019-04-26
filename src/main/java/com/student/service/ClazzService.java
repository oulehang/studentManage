package com.student.service;

import com.student.bean.Clazz;
import com.student.bean.DatagridResult;

import java.util.List;

/**
 * Created by Administrator on 2019/3/5.
 */
public interface ClazzService {
    List<Clazz> getAllClazz();

    List<Clazz> getClazzByGradeId(String gradeid);

    DatagridResult clazzDetailList(int page, int rows, Clazz clazz);

    int addClazz(String name, int gradeid);

    Clazz getClazzById(int clazzid);
}
