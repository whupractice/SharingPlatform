package com.example.demo.controller;

import com.example.demo.entity.TLEntity;
import com.example.demo.service.TLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ Author     ：Theory
 * @ Description：课程教师控制类
 */

@RestController
@RequestMapping("/tl")
public class TLController {

    @Autowired
    TLService tlService;

    /**
      * @Author      : QinYingran
      * @Description : 根据课程号获取教师授课列表
      * @Param       :
      * @return      :
      */
    @GetMapping("/{lessonId}")
    public List<TLEntity> getTLByLessonId(@PathVariable long lessonId) {
        return  tlService.getTLByLessonId(lessonId);
    }

    /**
      * @Author      : QinYingran
      * @Description : 向数据库插入教师授课记录
      * @Param       :
      * @return      :
      */
    @PutMapping("")
    public void insertTL(@RequestBody TLEntity tl) {
        tlService.insertTL(tl);
    }

    /**
      * @Author      : QinYingran
      * @Description : 更新教师授课记录
      * @Param       :
      * @return      :
      */
    @PutMapping("")
    public void updateTL(@RequestBody TLEntity tl) {
        tlService.insertTL(tl);
    }

    /**
      * @Author      : QinYingran
      * @Description : 通过课程号和教师号删除教师授课记录
      * @Param       :
      * @return      :
      */
    @DeleteMapping("/{lessonId}/{teacherId}")
    public void deleteTL(@PathVariable long lessonId, @PathVariable long teacherId) {
        tlService.deleteTL(lessonId, teacherId);
    }

}
