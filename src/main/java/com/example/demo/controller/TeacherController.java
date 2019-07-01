package com.example.demo.controller;

import com.example.demo.entity.TeacherEntity;
import com.example.demo.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ Author     ：Theory
 * @ Description：教师控制类
 */

@Api(value = "TeacherController|教师控制器")
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    /**
      * @Author      : QinYingran
      * @Description : 获取所有教师
      * @Param       : []
      * @return      : 教师列表
      */
    @ApiOperation(value = "获取所有教师", notes = "获取所有教师",httpMethod = "GET")
    @GetMapping("")
    public List<TeacherEntity> getAllTeacher() {
        return  teacherService.getAll();
    }


//    @ApiOperation(value = "获取所有教师并去重", notes = "获取所有教师并去重",httpMethod = "GET")
//    @GetMapping("/view")
//    public List<Object> getAllDistinctly() {
//        return  teacherService.getAllDistinctly();
//    }

    /**
      * @Author      : QinYingran
      * @Description : 向数据库插入教师
      * @Param       : [teacherEntity]
      * @return      : void
      */
    @ApiOperation(value = "向数据库插入教师", notes = "向数据库插入教师",httpMethod = "POST")
    @ApiParam(name = "teacherEntity",value = "教师实体,其中teacherId不能为空")
    @PostMapping("")
    public void insertTeacher(@RequestBody TeacherEntity teacherEntity) {
        teacherService.insertTeacher(teacherEntity);
    }

    /**
      * @Author      : QinYingran
      * @Description : 更新课程
      * @Param       : [teacherEntity]
      * @return      : void
      */
    @ApiOperation(value = "更新课程", notes = "更新课程",httpMethod = "PUT")
    @ApiParam(name = "teacherEntity",value = "教师实体,其中teacherId不能为空")
    @PutMapping("")
    public void updateTeacher(@RequestBody TeacherEntity teacherEntity) {
        teacherService.insertTeacher(teacherEntity);
    }

    /**
      * @Author      : QinYingran
      * @Description : 根据教师id删除教师
      * @Param       : [teacherId]
      * @return      : void
      */
    @ApiOperation(value = "根据教师id删除教师", notes = "根据教师id删除教师",httpMethod = "DELETE")
    @ApiParam(name = "teacherId",value = "教师账号")
    @DeleteMapping("")
    public void deleteTeacher(@RequestParam("teacherId") long teacherId) {
        teacherService.deleteTeacher(teacherId);
    }


    /**
      * @Author      : Theory
      * @Description : 根据关键字查询老师
      * @Param       : keyword -- 关键字
      * @return      : java.util.List<com.example.demo.entity.TeacherEntity>
      */
    @ApiOperation(value = "根据关键词查询老师", notes = "根据关键词查询老师",httpMethod = "GET")
    @GetMapping("/keyword")
    public List<Object> getTeacherByKeyword(@RequestParam String keyword){
        return teacherService.getTeacherByKeyword(keyword);
    }




    @ApiOperation(value = "根据id查询老师", notes = "根据id查询老师",httpMethod = "GET")
    @GetMapping("/id")
    public TeacherEntity getTeacherById(@RequestParam String id){
        return teacherService.getTeacherById(id);
    }


}
