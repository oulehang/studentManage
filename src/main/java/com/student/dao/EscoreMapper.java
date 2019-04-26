package com.student.dao;

import com.student.bean.EScore;
import com.student.bean.Exam;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EscoreMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(EScore eScore);
    List<EScore> getScoreById(@Param("examId") int examId, @Param("studentId") int studentId);

    void updateScoreById(@Param("id") String id, @Param("score") String score);

    List<Map<String,Object>> getScoreList(Exam exam);
}