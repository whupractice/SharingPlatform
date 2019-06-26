package com.example.demo.controller;

import com.example.demo.entity.TeacherEntity;
import com.example.demo.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ Author     ：Theory
 * @ Description：教师控制类
 */

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    /**
      * @Author      : QinYingran
      * @Description : 获取所有教师
      * @Param       :
      * @return      :
      */
    @GetMapping("")
    public List<TeacherEntity> getAllTeacher() {
        return  teacherService.getAll();
    }

    /**
      * @Author      : QinYingran
      * @Description : 向数据库插入教师
      * @Param       :
      * @return      :
      */
    @PostMapping("")
    public void insertTeacher(@RequestBody TeacherEntity teacherEntity) {
        teacherService.insertTeacher(teacherEntity);
    }

    /**
      * @Author      : QinYingran
      * @Description : 更新课程
      * @Param       :
      * @return      :
      */
    @PutMapping("")
    public void updateTeacher(@RequestBody TeacherEntity teacherEntity) {
        teacherService.insertTeacher(teacherEntity);
    }

    /**
      * @Author      : QinYingran
      * @Description : 根据教师id删除教师
      * @Param       :
      * @return      :
      */
    @DeleteMapping("")
    public void deleteTeacher(@RequestParam("teacher") long teacherId) {
        teacherService.deleteTeacher(teacherId);
    }

}
