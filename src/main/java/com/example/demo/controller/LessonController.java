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
      * @return      : 所有课程list
      */
    @GetMapping("")
    public List<LessonEntity> getAllLesson(){
        return lessonService.getAll();
    }


    /**
      * @Author      : Theory
      * @Description : 获取精品课程
      * @return      : 精品课程list
      */
    @GetMapping("/excellent")
    public List<LessonEntity> getExcellentLesson(){
        return lessonService.getExcellentLesson();
    }

    /**
      * @Author      : Theory
      * @Description : 获取热门课程
      * @Param       :  [lessonNum] -- 需要的热门课程数量
      * @return      : 热门课程list
      */
    @GetMapping("/hot")
    public List<LessonEntity> getHotLesson(@RequestParam("lessonNum") int num){
        return lessonService.getHotLesson(num);
    }


    /**
      * @Author      : Theory
      * @Description : 向数据库中插入课程
      * @Param       : [lesson] -- 课程
      */
    @PostMapping("")
    public void insertLesson(@RequestBody LessonEntity lesson){
        lessonService.insertLesson(lesson);
    }


    /**
      * @Author      : Theory
      * @Description : 更新课程
      * @Param       : [lesson] -- 课程
      */
    @PutMapping("")
    public void updateLesson(@RequestBody LessonEntity lesson){
        lessonService.insertLesson(lesson);
    }



    /**
      * @Author      : Theory
      * @Description : 根据课程id删除课程
      * @Param       : [lessonId] -- 课程id
      */
    @DeleteMapping("")
    public void deleteLesson(@RequestParam("lessonId") long lessonId){
        lessonService.deleteLesson(lessonId);
    }




}
