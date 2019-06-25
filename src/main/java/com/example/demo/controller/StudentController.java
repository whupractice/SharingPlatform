package com.example.demo.controller;

import com.example.demo.entity.StudentEntity;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ Author     ：Theory
 * @ Description：学生控制类
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;


    /**
      * @Author      : Theory
      * @Description : 登陆验证
      * @Param       : [user, pwd] -- 学生账号，密码
      * @return      : boolean
      */
    @GetMapping(value = "/login")
    public boolean login(@RequestParam(value = "user") String user,
                        @RequestParam(value = "pwd") String pwd){
        return studentService.getPwdById(user,pwd);
    }

    /**
      * @Author      : Theory
      * @Description : 注册学生账号
      * @Param       : [pwd] -- 学生密码
      * @return      : 学生账号
      */
    @PostMapping(value = "/register")
    public long register(@RequestBody StudentEntity stu){
        return studentService.register(stu);
    }



    /**
      * @Author      : Theory
      * @Description : 返回数据库中所有学生的信息
      * @return      : 所有学生信息
      */
    @GetMapping(value = "")
    public List<StudentEntity> getAllStudent(){
        return studentService.getAllStudent();
    }


    /**
      * @Author      : Theory
      * @Description : 向数据库中插入学生信息
      * @Param       : [stu] -- 学生实体
      */
    @PostMapping("")
    public void insertStudent(@RequestBody StudentEntity stu){
        studentService.insertStudent(stu);
    }


    /**
     * @Author      : Theory
     * @Description : 更新指定学生记录
     * @Param       : [stu] -- 学生实体
     */
    @PutMapping("")
    public void updateStudent(@RequestBody StudentEntity stu){
        studentService.insertStudent(stu);
    }

    /**
     * @Author      : Theory
     * @Description : 删除数据库中学生信息
     * @Param       : [studentId] -- 学生账号
     */
    @DeleteMapping("")
    public void deleteLesson(@RequestParam("studentId") long studentId){
        studentService.deleteStudent(studentId);
    }

}
