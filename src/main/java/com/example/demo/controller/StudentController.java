package com.example.demo.controller;

import com.example.demo.entity.LessonEntity;
import com.example.demo.entity.StudentEntity;
import com.example.demo.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
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

    /**
      * @Author      : Theory
      * @Description : 登陆验证
      * @Param       : [user, pwd] -- 学生账号，密码
      * @return      : 学生实体
      */
    @ApiOperation(value = "登陆验证", notes = "登陆验证",httpMethod = "POST")
    @PostMapping(value = "/login")
    public StudentEntity login(@RequestBody StudentEntity student) {
        return studentService.judgeLogin(student.getPhone(),student.getPwd());
    }




    /**
      * @Author      : Theory
      * @Description : 注册学生账号
      * @Param       : [pwd] -- 学生密码
      * @return      : 学生账号
      */
    @ApiOperation(value = "注册学生账号", notes = "注册学生账号",httpMethod = "POST")
    @PostMapping(value = "/register")
    @ApiParam(name = "stu",value = "学生实体,其中phone和pwd不能为空")
    public boolean register(@RequestBody StudentEntity stu){
        return studentService.register(stu);
    }



    @ApiOperation(value = "根据id获取学生信息", notes = "根据id获取学生信息",httpMethod = "GET")
    @GetMapping(value = "/info")
    public StudentEntity getStuById(@RequestParam(value = "phone") @ApiParam(value = "学生账号") String phone){
        return studentService.getStuById(phone);
    }

    @ApiOperation(value = "根据昵称获取学生", notes = "根据昵称获取学生",httpMethod = "GET")
    @GetMapping(value = "/nickName")
    @ApiParam(name = "nickName",value = "昵称")
    public StudentEntity getStuByNickName(@RequestParam String nickName) {
        return studentService.getStuByNickName(nickName);
    }


    @ApiOperation(value = "根据学校名获取学生", notes = "根据学校名获取学生",httpMethod = "GET")
    @GetMapping(value = "/schoolName")
    @ApiParam(name = "schoolName",value = "学校名")
    public List<StudentEntity> getStuBySchoolName(@RequestParam String schoolName) {
        return studentService.getStuBySchoolName(schoolName);
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


    @ApiOperation(value = "返回数据库中所有系统管理员的信息", notes = "返回数据库中所有系统管理员的信息",httpMethod = "GET")
    @GetMapping(value = "/manager")
    public List<StudentEntity> getAllManager() {
        return studentService.getAllManager();
    }


    /**
     * @Author      : Theory
     * @Description : 更新指定学生记录
     * @Param       : [stu] -- 学生实体
     */
    @ApiOperation(value = "更新指定学生记录", notes = "不允许改phone！！！",httpMethod = "PUT")
    @ApiParam(name = "stu",value = "学生实体,其中phone和pwd不能为空")
    @PutMapping("")
    public boolean updateStudent(@RequestBody StudentEntity stu){
        return studentService.insertStudent(stu);
    }

    /**
     * @Author      : Theory
     * @Description : 删除数据库中学生信息
     * @Param       : [studentId] -- 学生账号
     */
    @ApiOperation(value = "删除数据库中学生信息", notes = "删除数据库中学生信息",httpMethod = "DELETE")
    @ApiParam(name = "studentId",value = "学生账号")
    @DeleteMapping("")
    public void deleteLesson(@RequestParam("phone") long phone){
        studentService.deleteStudent(phone);
    }




    public Specification<LessonEntity> createSpecification(String schoolName) {

        return (Specification<LessonEntity>) (root, query, cb) -> {
            //用于暂时存放查询条件的集合
            List<Predicate> predicatesList = new ArrayList<>();


            Predicate predicate = cb.like(root.get("schoolName"), "%" + schoolName + "%");
            predicatesList.add(predicate);

            Predicate[] predicates = new Predicate[predicatesList.size()];
            return cb.and(predicatesList.toArray(predicates));
        };

    }

}
