package com.example.demo.controller;

import com.example.demo.entity.SchoolEntity;
import com.example.demo.service.SchoolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ Author     ：Theory
 * @ Description：院校控制类
 */

@Api(value = "SchoolController|学校控制器")
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
    @ApiOperation(value = "获取所有学校", notes = "获取所有学校",httpMethod = "GET")
    @GetMapping("")
    public List<SchoolEntity> getAllSchool(){
        return schoolService.getAll();
    }


//    @ApiOperation(value = "获取所有学校并去重", notes = "获取所有学校并去重",httpMethod = "GET")
//    @GetMapping("/view")
//    public List<Object> getAllDistinctly(){
//        return schoolService.getAllDistinctly();
//    }


    @ApiOperation(value = "根据id获取学校介绍", notes = "根据id获取学校介绍",httpMethod = "GET")
    @ApiParam(name = "id",value = "学校id")
    @GetMapping("/getSchoolInfoById")
    public String getSchoolInfoById (Long id) {
        return schoolService.getSchoolInfoById(id);
    }

    @ApiOperation(value = "根据关键词查询学校", notes = "根据关键词查询学校",httpMethod = "GET")
    @ApiParam(name = "keyword",value = "学校名关键词")
    @GetMapping("/keyword")
    public List<SchoolEntity> getSchoolByKeyword(@RequestParam String keyword) {
        return schoolService.getSchoolByKeyword(keyword);
    }


    @ApiOperation(value = "根据名称查询学校", notes = "根据名称查询学校",httpMethod = "GET")
    @ApiParam(name = "schoolName",value = "学校名关键词")
    @GetMapping("/schoolName")
    public SchoolEntity getSchoolByName(@RequestParam(value = "schoolName") String schoolName) {
        return schoolService.getSchoolByName(schoolName);
    }

    /**
      * @Author      : QinYingran
      * @Description : 向数据库插入学校
      * @Param       : [school]
      * @return      : void
      */
    @ApiOperation(value = "向数据库插入学校", notes = "向数据库插入学校",httpMethod = "POST")
    @ApiParam(name = "school",value = "学校实体,其中schoolId不能为空")
    @PostMapping("")
    public void insertSchool(@RequestBody SchoolEntity school) {
        schoolService.insertSchool(school);
    }


    /**
      * @Author      : QinYingran
      * @Description : 更新学校
      * @Param       : [school]
      * @return      : void
      */
    @ApiOperation(value = "更新学校", notes = "更新学校",httpMethod = "PUT")
    @ApiParam(name = "school",value = "学校实体,其中schoolId不能为空")
    @PutMapping("")
    public void updateSchool(@RequestBody SchoolEntity school) {
        schoolService.insertSchool(school);
    }



    /**
      * @Author      : QinYingran
      * @Description :根据id删除学校
      * @Param       : [schoolId]
      * @return      : void
      */
    @ApiOperation(value = "根据id删除学校", notes = "根据id删除学校",httpMethod = "DELETE")
    @ApiParam(name = "schoolId",value = "schoolId")
    @DeleteMapping("")
    public void deleteSchool(@RequestParam("schoolId") long schoolId) {
        schoolService.deleteSchool(schoolId);
    }

}
