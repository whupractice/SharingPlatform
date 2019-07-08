package com.example.demo.controller;

import com.example.demo.entity.LessonEntity;
import com.example.demo.entity.TLEntity;
import com.example.demo.entity.TeacherEntity;
import com.example.demo.service.TLService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ Author     ：Theory
 * @ Description：课程教师控制类
 */

@Api(value = "TLController|教师授课控制器")
@RestController
@RequestMapping("/tl")
public class TLController {

    @Autowired
    TLService tlService;


    @ApiOperation(value = "获取所有教师授课信息", notes = "获取所有教师授课信息",httpMethod = "GET")
    @GetMapping("")
    public List<TLEntity> getAllTL() {
        return tlService.getAll();
    }

    /**
      * @Author      : QinYingran
      * @Description : 根据课程号获取教师授课列表
      * @Param       :
      * @return      :
      */
    @ApiOperation(value = "根据课程号获取教师授课列表", notes = "根据课程号获取教师授课列表",httpMethod = "GET")
    @ApiParam(name = "lessonId",value = "课程号")
    @GetMapping("/lessonId")
    public List<TLEntity> getTLByLessonId(@RequestParam String lessonId) {
        return  tlService.getTLByLessonId(lessonId);
    }

    @ApiOperation(value = "根据教师号获取授课列表", notes = "根据教师号获取授课列表",httpMethod = "GET")
    @ApiParam(name = "teacherId",value = "教师号")
    @GetMapping("/teacherId")
    public List<TLEntity> getTLByTeacherId(@RequestParam String teacherId) {
        return  tlService.getAllByTeacherId(teacherId);
    }

    @ApiOperation(value = "根据课程号获取教师列表", notes = "根据课程号获取教师列表",httpMethod = "GET")
    @ApiParam(name = "lessonId",value = "课程号")
    @GetMapping("/getTeacherByLessonId")
    public List<TeacherEntity> getTeacherByLessonId(@RequestParam String lessonId) {
        return tlService.getTeacherByLessonId(lessonId);
    }

    @ApiOperation(value = "根据教师号获取课程列表", notes = "根据教师号获取课程列表",httpMethod = "GET")
    @ApiParam(name = "teacherId",value = "教师号")
    @GetMapping("/getLessonByTeacherId")
    public List<LessonEntity> getLessonByTeacherId(@RequestParam String teacherId) {
        return tlService.getLessonByTeacherId(teacherId);
    }


    /**
      * @Author      : QinYingran
      * @Description : 向数据库插入教师授课记录
      * @Param       :
      * @return      :
      */
    @PreAuthorize("hasRole('lessonManager')")
    @ApiOperation(value = "向数据库插入教师授课信息", notes = "向数据库插入教师授课信息",httpMethod = "POST")
    @ApiParam(name = "tl",value = "教师授课实体，其中teacherId、lessonId不能为空")
    @PostMapping("")
    public void insertTL(@RequestBody TLEntity tl) {
        tlService.insertTL(tl);
    }

    /**
      * @Author      : QinYingran
      * @Description : 更新教师授课记录
      * @Param       :
      * @return      :
      */
    @ApiOperation(value = "更新教师授课记录", notes = "更新教师授课记录",httpMethod = "PUT")
    @ApiParam(name = "tl",value = "教师授课实体，其中teacherId、lessonId不能为空")
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
    @ApiOperation(value = "通过课程号和教师号删除教师授课记录", notes = "通过课程号和教师号删除教师授课记录",httpMethod = "DELETE")
    @DeleteMapping("/lessonId/teacherId")
    public void deleteTL(@RequestParam @ApiParam(value = "课程号") String lessonId, @RequestParam @ApiParam(value = "教师账号") String teacherId) {
        tlService.deleteTL(lessonId, teacherId);
    }

    @PreAuthorize("hasRole('lessonManager')")
    @ApiOperation(value = "通过教师号删除教师授课记录", notes = "通过教师号删除教师授课记录",httpMethod = "DELETE")
    @DeleteMapping("/teacherId")
    public void deleteTLByTeacherId(@RequestParam @ApiParam(value = "教师账号") String teacherId) {
        tlService.deleteTLByTeacherId(teacherId);
    }

}
