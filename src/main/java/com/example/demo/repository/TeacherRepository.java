package com.example.demo.repository;

import com.example.demo.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
  * @Author      : QinYingran
  * @Description : 教师表接口
  */
public interface TeacherRepository extends JpaRepository<TeacherEntity,Long> {

    //获取最大的课程id
    @Query(value = "SELECT MAX(teacher_id) FROM teacher",nativeQuery = true)
    long getNewTeacherId();
}
