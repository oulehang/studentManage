package com.student.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.student.bean.Clazz;
import com.student.bean.DatagridResult;
import com.student.bean.Grade;
import com.student.dao.ClazzMapper;
import com.student.dao.GradeMapper;
import com.student.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2019/3/5.
 */
@Service
public class ClazzServiceImpl implements ClazzService{
    private final int SUCCESS=1;//成功
    private final int FAILURE=2;//失败
    private final int EXIST=3;//失败，已存在
    @Autowired
    private ClazzMapper clazzMapper;

    @Autowired
    private  GradeMapper gradeMapper;

    @Override
    public List<Clazz> getAllClazz() {
        return clazzMapper.getAllClazz();
    }

    @Override
    public List<Clazz> getClazzByGradeId(String gradeid) {
        return clazzMapper.selectByGradeId(Integer.parseInt(gradeid));
    }

    @Override
    public DatagridResult clazzDetailList(int page, int rows, Clazz clazz) {
        PageHelper.startPage(page,rows);
        List<Clazz> clazzList=clazzMapper.getClazzBy(clazz);
        for (Clazz c: clazzList ) {
            Grade grade=gradeMapper.selectByPrimaryKey(c.getGradeid());
            c.setGrade(grade);
        }
        //得到分页的结果对象
        PageInfo<Clazz> pageInfo = new PageInfo<Clazz>(clazzList);
        DatagridResult result = new DatagridResult();
        result.setTotal(pageInfo.getTotal()); //设置总记录数
        result.setRows(clazzList);  //设置每页展示数据集合
        return result;
    }

    @Override
    public int addClazz(String name, int gradeid) {
        Clazz clazz=clazzMapper.selectByName(name,gradeid);
        if(clazz!=null){
            return EXIST;
        }
        Clazz clazz1=new Clazz();
        clazz1.setGradeid(gradeid);
        clazz1.setName(name);
        int i = clazzMapper.insert(clazz1);
        if(i>0){
            return SUCCESS;
        }else {
            return FAILURE;
        }
    }

    @Override
    public Clazz getClazzById(int clazzid) {
        return clazzMapper.selectByPrimaryKey(clazzid);
    }
}
