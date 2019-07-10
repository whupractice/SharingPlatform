package com.example.demo.repository;

import com.example.demo.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
  * @Author      : Theory
  * @Description : 学生表接口
  */
public interface StudentRepository extends JpaRepository<StudentEntity,Long>, JpaSpecificationExecutor<StudentEntity> {


    //通过学生账号查询学生，以便于进行登陆验证
    @Query(value = "SELECT * FROM student WHERE phone = ?1",nativeQuery = true)
    StudentEntity getStuById(Long phone);

//    //返回现在student_id中最大的值（新注册的）
//    @Query(value = "SELECT MAX(student_id) FROM student",nativeQuery = true)
//    long getMaxId();

    //根据昵称查询学生
    @Query(value = "SELECT * FROM student WHERE nick_name = ?1",nativeQuery = true)
    StudentEntity getStuByNickName(String nickName);


    //根据学校名查询学生
    @Query(value = "SELECT * FROM student WHERE school_name = ?1",nativeQuery = true)
    List<StudentEntity> getStuBySchoolName(String schoolName);

    @Query(value = "select * from student where is_manager = 1",nativeQuery = true)
    List<StudentEntity> getAllManager();

    @Query(value = "select * from student where phone = ?1",nativeQuery = true)
    StudentEntity getNickNameByPhone(long phone);

}
