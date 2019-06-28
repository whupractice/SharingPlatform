package com.example.demo.controller;

import com.example.demo.entity.LessonEntity;
import com.example.demo.service.LessonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @ Author     ：Theory
 * @ Description：课程控制类
 */

@Api(value = "LessonController|课程控制器")
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
    @ApiOperation(value = "获取所有课程", notes = "获取所有课程",httpMethod = "GET")
    @GetMapping("")
    public List<LessonEntity> getAllLesson(){
        return lessonService.getAll();
    }


    @ApiOperation(value = "获取所有课程并去重", notes = "获取所有课程并去重",httpMethod = "GET")
    @GetMapping("/view")
    public List<Object> getAllDistinctly(){
        return lessonService.getAllDistinctly();
    }

    /**
      * @Author      : Theory
      * @Description : 获取精品课程
      * @return      : 精品课程list
      */
    @ApiOperation(value = "获取精品课程", notes = "获取精品课程",httpMethod = "GET")
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
    @ApiOperation(value = "获取n个热门课程", notes = "获取n个热门课程",httpMethod = "GET")
    @ApiParam(name = "lessonNum",value = "所要获取热门课程数")
    @GetMapping("/hot")
    public List<LessonEntity> getHotLesson(@RequestParam("lessonNum") int lessonNum){
        return lessonService.getHotLesson(lessonNum);
    }


    /**
      * @Author      : Theory
      * @Description : 向数据库中插入课程
      * @Param       : [lesson] -- 课程
      */
    @ApiOperation(value = "向数据库中插入课程", notes = "向数据库中插入课程",httpMethod = "POST")
    @ApiParam(name = "lesson",value = "课程实体，其中lessonId、credit、isExcellent、shareNum、welcome不能为空")
    @PostMapping("")
    public void insertLesson(@RequestBody LessonEntity lesson){
        lessonService.insertLesson(lesson);
    }


    /**
      * @Author      : Theory
      * @Description : 更新课程
      * @Param       : [lesson] -- 课程
      */
    @ApiOperation(value = "更新课程", notes = "更新课程",httpMethod = "PUT")
    @ApiParam(name = "lesson",value = "课程实体，其中lessonId、credit、isExcellent、shareNum、welcome不能为空")
    @PutMapping("")
    public void updateLesson(@RequestBody LessonEntity lesson){
        lessonService.insertLesson(lesson);
    }



    /**
      * @Author      : Theory
      * @Description : 根据课程id删除课程
      * @Param       : [lessonId] -- 课程id
      */
    @ApiOperation(value = "根据课程id删除课程", notes = "根据课程id删除课程",httpMethod = "DELETE")
    @ApiParam(name = "lessonId",value = "课程号")
    @DeleteMapping("")
    public void deleteLesson(@RequestParam("lessonId") long lessonId){
        lessonService.deleteLesson(lessonId);
    }




}
