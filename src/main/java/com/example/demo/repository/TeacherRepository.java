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
    @Query(value = "select distinct academy_name,img_link,job,school_name,teacher_intro,teacher_name from teacher order by school_name",nativeQuery = true)
    List<Object> getAllDistinctly();

    //根据关键字查询老师
    @Query(value = "select distinct teacher_name,school_name,academy_name,job,teacher_intro,img_link from teacher where teacher_name like ?1 order by school_name",nativeQuery = true)
    List<Object> getTeacherByKeyword(String keyword);
}
