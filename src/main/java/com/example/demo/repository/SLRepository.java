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


    //查询特定学生号的选课信息
    @Query(value = "SELECT * FROM SL WHERE student_id = ?1",nativeQuery = true)
    List<SLEntity> getAllByStuId(long studentId);


    //查询选了这门课的学生数量
    @Query(value = "SELECT COUNT(student_id) FROM sl WHERE Lesson_id=?1 group by Lesson_id",nativeQuery = true)
    int getStuNumByLessonId(long lessonId);

}
