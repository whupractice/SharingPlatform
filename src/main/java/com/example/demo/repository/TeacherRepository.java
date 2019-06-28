package com.example.demo.repository;

import com.example.demo.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
  * @Author      : QinYingran
  * @Description : 教师表接口
  */
public interface TeacherRepository extends JpaRepository<TeacherEntity,Long> {

    //获取最大的教师id
    @Query(value = "SELECT MAX(teacher_id) FROM teacher",nativeQuery = true)
    long getNewTeacherId();

    //获取所有教师列表
    @Query(value = "SELECT * FROM teacher_view",nativeQuery = true)
    List<TeacherEntity> getAllTeacher();
}
