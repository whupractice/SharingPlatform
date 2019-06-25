package com.example.demo.controller;

import com.example.demo.entity.LessonEntity;
import com.example.demo.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @ Author     ：Theory
 * @ Description：课程控制类
 */

@RestController
@RequestMapping("/lesson")
public class LessonController {


    @Autowired
    LessonService lessonService;


    /**
      * @Author      : Theory
      * @Description : 获取所有课程
      * @return      : java.util.List<com.example.demo.entity.LessonEntity>
      */
    @GetMapping("")
    public List<LessonEntity> getAllLesson(){
        return lessonService.getAll();
    }


    /**
      * @Author      : Theory
      * @Description : 获取精品课程
      * @return      : java.util.List<com.example.demo.entity.LessonEntity>
      */
    @GetMapping("/excellent")
    public List<LessonEntity> getExcellentLesson(){
        return lessonService.getExcellentLesson();
    }

    /**
      * @Author      : Theory
      * @Description : 获取热门课程
      * @return      : java.util.List<com.example.demo.entity.LessonEntity>
      */
    @GetMapping("/hot")
    public List<LessonEntity> getHotLesson(@RequestParam("lessonNum") int num){
        return lessonService.getHotLesson(num);
    }


    /**
      * @Author      : Theory
      * @Description : 向数据库中插入课程
      * @Param       : [lesson]
      */
    @PostMapping("")
    public void insertLesson(@RequestBody LessonEntity lesson){
        lessonService.insertLesson(lesson);
    }






}
