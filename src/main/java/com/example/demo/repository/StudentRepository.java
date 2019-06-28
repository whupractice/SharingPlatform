package com.example.demo.repository;

import com.example.demo.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
  * @Author      : Theory
  * @Description : 学生表接口
  */
public interface StudentRepository extends JpaRepository<StudentEntity,Long> {


    //通过学生账号查询学生密码，以便于进行登陆验证
    @Query(value = "SELECT pwd FROM student WHERE student_id = ?1",nativeQuery = true)
    String getPwdById(Long id);

    //返回现在student_id中最大的值（新注册的）
    @Query(value = "SELECT MAX(student_id) FROM student",nativeQuery = true)
    long getMaxId();

    //获取所有学生
    @Query(value = "SELECT * from student_view",nativeQuery = true)
    List<StudentEntity> getAllStudent();
}
