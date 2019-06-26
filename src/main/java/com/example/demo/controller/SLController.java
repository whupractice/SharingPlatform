package com.example.demo.controller;

import com.example.demo.entity.SLEntity;
import com.example.demo.service.SLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ Author     ：Theory
 * @ Description：学生选课控制类
 */

@RestController
@RequestMapping("/sl")
public class SLController {

    @Autowired
    SLService slService;

    /**
      * @Author      : Theory
      * @Description : 根据学生账号获取所选课程
      * @Param       : [studId] -- 学生账号
      * @return      : 选课记录list
      */
    @GetMapping("/{stuId}")
    public List<SLEntity> getSLByStuId(@PathVariable long stuId){
        return slService.getSLByStuId(stuId);
    }


    /**
      * @Author      : Theory
      * @Description : 通过课程号获取选择这门课程的学生数量
      * @Param       : [lessonId] -- 课程id
      * @return      : 选这门课的学生数量
      */
    @GetMapping("")
    public int getStuNumByLessonId(@RequestParam long lessonId){
        return slService.getStuNumByLessonId(lessonId);
    }


    /**
      * @Author      : Theory
      * @Description : 向数据库中插入学生选课记录
      * @Param       : [sl] -- 学生选课记录
      */
    @PostMapping("")
    public void insertSL(@RequestBody SLEntity sl){
        slService.insertSL(sl);
    }



    /**
      * @Author      : Theory
      * @Description : 更新数据库中学生选课记录
      * @Param       : [sl] -- 新的学生选课记录
      */
    @PutMapping("")
    public void updateSL(@RequestBody SLEntity sl){slService.insertSL(sl);}


    /**
     * @Author      : Theory
     * @Description : 通过学生账号和课程账号删除选课记录
     * @Param       : [stuId,lessonId] -- 学生账号、课程账号
     */
    @DeleteMapping("/{stuId}/{lessonId}")
    public void deleteSL(@PathVariable long stuId,
                         @PathVariable long lessonId){
        slService.deleteSL(stuId,lessonId);
    }

}
