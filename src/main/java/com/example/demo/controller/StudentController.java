package com.example.demo.controller;

import com.example.demo.entity.StudentEntity;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
      * @Param       : [user, pwd]
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
      * @Param       : [pwd]
      * @return      : long
      */
    @PostMapping(value = "/register")
    public long register(@RequestBody StudentEntity stu){
        return studentService.register(stu);
    }



}
