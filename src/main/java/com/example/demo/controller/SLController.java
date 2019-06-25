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
      * @Description : 向数据库中插入学生选课记录
      * @Param       : [sl] -- 学生选课记录
      * @return      : void
      */
    @PostMapping("")
    public void insertSL(@RequestBody SLEntity sl){
        slService.insertSL(sl);
    }

}
