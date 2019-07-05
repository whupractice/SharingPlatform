package com.example.demo.repository;

import com.example.demo.entity.SLEntity;
import com.example.demo.keys.SLKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
  * @Author      : Theory
  * @Description : 学生选课表接口
  */
public interface SLRepository extends JpaRepository<SLEntity, SLKeys> {


    //根据学生id查询学生选课信息
    @Query(value = "SELECT * FROM sl WHERE phone = ?1",nativeQuery = true)
    List<SLEntity> getSLByStuId(long studentId);


    //查询选了这门课的学生数量
    @Query(value = "SELECT COUNT(phone) FROM sl WHERE Lesson_id=?1 group by Lesson_id",nativeQuery = true)
    int getStuNumByLessonId(long lessonId);


    //根据课程id查询学生选课信息
    @Query(value = "SELECT * FROM sl WHERE lesson_id = ?1",nativeQuery = true)
    List<SLEntity> getSLByLessonId(long lessonId);

    //根据课程id查询评论
    @Query(value = "select * from sl where lesson_id = ?1 and evaluation is not null",nativeQuery = true)
    List<SLEntity> getEvaluationByLessonId(long lessonId);

}
