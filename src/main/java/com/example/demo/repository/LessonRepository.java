package com.example.demo.repository;

import com.example.demo.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LessonRepository extends JpaRepository<LessonEntity,Long> {

    //查询精品课程
    @Query(value = "SELECT * FROM lesson WHERE is_excellent = 1",nativeQuery = true)
    List<LessonEntity> getExcellentClass();




}
