package com.example.demo.controller;

import com.example.demo.entity.StudentEntity;
import com.example.demo.service.SpecUtil;
import com.example.demo.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    SpecUtil specUtil;

    /**
      * @Author      : Theory
      * @Description : 登陆验证
      * @Param       : [user, pwd] -- 学生账号，密码
      * @return      : 学生实体
      */
    @ApiOperation(value = "登陆验证", notes = "登陆验证",httpMethod = "POST")
    @PostMapping(value = "/login")
    public String login(@RequestBody StudentEntity student) {
        return studentService.judgeLogin(student.getPhone(),student.getPwd());
    }

    @ApiOperation(value = "退出登录", notes = "退出登录",httpMethod = "DELETE")
    @DeleteMapping(value = "/logout")
    public boolean logout(HttpServletRequest request){
        String token;
        String requestHeader = request.getHeader("Authorization");
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            token = requestHeader.substring(7);
            studentService.logout(token);
            return true;
        } else {
            return false;
        }
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
    public String register(@RequestBody StudentEntity stu){
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
    @ApiOperation(value = "获取数据库中所有学生的信息", notes = "获取数据库中所有学生的信息",httpMethod = "GET")
    @GetMapping(value = "")
    public List<StudentEntity> getAllStudent(){
        return studentService.getAllStudent();
    }


    @ApiOperation(value = "获取数据库中所有系统管理员的信息", notes = "获取数据库中所有系统管理员的信息",httpMethod = "GET")
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


    @ApiOperation(value = "分页获取所有课程管理员", notes = "分页获取所有课程管理员",httpMethod = "GET")
    @GetMapping("/lessonManagerPages")
    public Page<StudentEntity> getLessonManagerPages(@PageableDefault(size = 5, sort = {"academyName"}, direction = Sort.Direction.DESC)@ApiParam(value = "分页信息") Pageable pageable) {

        Specification<StudentEntity> specification = specUtil.createLessonManagerSpecification();
        return studentService.getAll(specification,pageable);
    }

    @ApiOperation(value = "分页根据姓名获取所有课程管理员", notes = "分页根据姓名获取所有课程管理员",httpMethod = "GET")
    @GetMapping("/lessonManagerPagesByName")
    public Page<StudentEntity> getLessonManagerPagesByName(@PageableDefault(size = 5, sort = {"academyName"}, direction = Sort.Direction.DESC)@ApiParam(value = "分页信息") Pageable pageable,
                                                           @RequestParam(value = "realName")@ApiParam(value = "课程管理员名") String realName) {

        Specification<StudentEntity> specification = specUtil.createLessonManagerSpecificationByName(realName);
        return studentService.getAll(specification,pageable);
    }

    @ApiOperation(value = "分页根据学校获取所有课程管理员", notes = "分页根据学校获取所有课程管理员",httpMethod = "GET")
    @GetMapping("/lessonManagerPagesBySchool")
    public Page<StudentEntity> getLessonManagerPagesBySchool(@PageableDefault(size = 5, sort = {"academyName"}, direction = Sort.Direction.DESC)@ApiParam(value = "分页信息") Pageable pageable,
                                                             @RequestParam(value = "schoolName")@ApiParam(value = "学校名") String schoolName) {

        Specification<StudentEntity> specification = specUtil.createLessonManagerSpecificationBySchool(schoolName);
        return studentService.getAll(specification,pageable);
    }








}
