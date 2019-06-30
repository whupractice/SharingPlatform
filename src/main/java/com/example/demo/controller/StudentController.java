package com.example.demo.controller;

import com.example.demo.entity.StudentEntity;
import com.example.demo.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ Author     ：Theory
 * @ Description：学生控制类
 */

@Api(value = "StudentController|学生控制器")
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;


    private StudentEntity currentUser = null;  //当前用户

    /**
      * @Author      : Theory
      * @Description : 登陆验证
      * @Param       : [user, pwd] -- 学生账号，密码
      * @return      : boolean
      */
    @ApiOperation(value = "登陆验证", notes = "登陆验证",httpMethod = "GET")
    @GetMapping(value = "/login")
    public boolean login(@RequestParam(value = "user") @ApiParam(value = "学生账号")
                                     String user,
                        @RequestParam(value = "pwd") @ApiParam(value = "密码") String pwd){
        return studentService.judgeLogin(user,pwd);
    }



    /**
      * @Author      : Theory
      * @Description : 管理员登陆验证
      * @Param       : [user, pwd] -- 管理员账号、密码
      * @return      : boolean
      */
    @ApiOperation(value = "登陆验证", notes = "登陆验证",httpMethod = "GET")
    @GetMapping(value = "/login/manager")
    public boolean managerLogin(@RequestParam(value = "user") @ApiParam(value = "学生账号")
                                 String user,
                         @RequestParam(value = "pwd") @ApiParam(value = "密码") String pwd){
        return studentService.judgeMLogin(user,pwd);
    }

    /**
     * @Author      : Theory
     * @Description : 返回当前用户
     * @return      : 当前在线用户信息
     */
    @ApiOperation(value = "返回当前用户", notes = "当前在线用户信息",httpMethod = "GET")
    @GetMapping(value = "/cu")
    public StudentEntity getCurrentUser(){
        return currentUser;
    }

    /**
      * @Author      : Theory
      * @Description : 注册学生账号
      * @Param       : [pwd] -- 学生密码
      * @return      : 学生账号
      */
    @ApiOperation(value = "注册学生账号", notes = "注册学生账号",httpMethod = "POST")
    @PostMapping(value = "/register")
    @ApiParam(name = "stu",value = "学生实体,其中pwd不能为空")
    public long register(@RequestBody StudentEntity stu){
        return studentService.register(stu);
    }



    @ApiOperation(value = "根据id获取学生信息", notes = "根据id获取学生信息",httpMethod = "GET")
    @GetMapping(value = "/{studentId}")
    public StudentEntity getStuById(@PathVariable @ApiParam(value = "学生账号") String user){
        return studentService.getStuById(user);
    }



    /**
      * @Author      : Theory
      * @Description : 返回数据库中所有学生的信息
      * @return      : 所有学生信息
      */
    @ApiOperation(value = "返回数据库中所有学生的信息", notes = "返回数据库中所有学生的信息",httpMethod = "GET")
    @GetMapping(value = "")
    public List<StudentEntity> getAllStudent(){
        return studentService.getAllStudent();
    }


    /**
      * @Author      : Theory
      * @Description : 向数据库中插入学生信息
      * @Param       : [stu] -- 学生实体
      */
    @ApiOperation(value = "向数据库中插入学生信息", notes = "向数据库中插入学生信息",httpMethod = "POST")
    @ApiParam(name = "stu",value = "学生实体,其中studentId不能为空")
    @PostMapping("")
    public void insertStudent(@RequestBody StudentEntity stu){
        studentService.insertStudent(stu);
    }


    /**
     * @Author      : Theory
     * @Description : 更新指定学生记录
     * @Param       : [stu] -- 学生实体
     */
    @ApiOperation(value = "更新指定学生记录", notes = "更新指定学生记录",httpMethod = "PUT")
    @ApiParam(name = "stu",value = "学生实体,其中studentId不能为空")
    @PutMapping("")
    public void updateStudent(@RequestBody StudentEntity stu){
        studentService.insertStudent(stu);
    }

    /**
     * @Author      : Theory
     * @Description : 删除数据库中学生信息
     * @Param       : [studentId] -- 学生账号
     */
    @ApiOperation(value = "删除数据库中学生信息", notes = "删除数据库中学生信息",httpMethod = "DELETE")
    @ApiParam(name = "studentId",value = "学生账号")
    @DeleteMapping("")
    public void deleteLesson(@RequestParam("studentId") long studentId){
        studentService.deleteStudent(studentId);
    }



}
