package com.example.demo.repository;

import com.example.demo.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
  * @Author      : Theory
  * @Description : 课程表接口
  */
public interface LessonRepository extends JpaRepository<LessonEntity,Long> {

    //查询精品课程
    @Query(value = "SELECT * FROM lesson WHERE is_excellent = 1",nativeQuery = true)
    List<LessonEntity> getExcellentClass();

    //返回最大的课程id（最新加入的课程id）
    @Query(value = "SELECT MAX(lesson_id) FROM lesson",nativeQuery = true)
    long getNewLessonId();

    //查询全部课程
    @Query(value = "SELECT * FROM lesson_view",nativeQuery = true)
    List<LessonEntity> getAllClass();

}
