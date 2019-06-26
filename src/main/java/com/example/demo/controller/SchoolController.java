package com.example.demo.controller;

import com.example.demo.entity.SchoolEntity;
import com.example.demo.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ Author     ：Theory
 * @ Description：院校控制类
 */

@RestController
@RequestMapping("/school")
public class SchoolController {

    @Autowired
    SchoolService schoolService;

    /**
      * @Author      : QinYingran
      * @Description : 获取所有学校
      * @Param       :
      * @return      : 所有学校list
      */
    @GetMapping("")
    public List<SchoolEntity> getAllSchool(){
        return schoolService.getAll();
    }

    /**
      * @Author      : QinYingran
      * @Description : 向数据库插入课程
      * @Param       :
      */
    @PostMapping("")
    public void insertSchool(@RequestBody SchoolEntity school) {
        schoolService.insertSchool(school);
    }

    /**
      * @Author      : QinYingran
      * @Description : 更新课程
      * @Param       :
      * @return      :
      */
    @PutMapping("")
    public void updateSchool(@RequestBody SchoolEntity school) {
        schoolService.insertSchool(school);
    }

    @DeleteMapping("")
    public void deleteSchool(@RequestParam("schoolId") long schoolId) {
        schoolService.deleteSchool(schoolId);
    }

}
