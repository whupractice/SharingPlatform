package com.example.demo.controller;

import com.example.demo.entity.TLEntity;
import com.example.demo.service.TLService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
      * @Author      : QinYingran
      * @Description : 根据课程号获取教师授课列表
      * @Param       :
      * @return      :
      */
    @ApiOperation(value = "根据课程号获取教师授课列表", notes = "根据课程号获取教师授课列表",httpMethod = "GET")
    @ApiParam(name = "lessonId",value = "课程号")
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
    @ApiOperation(value = "根据课程号获取教师授课列表", notes = "根据课程号获取教师授课列表",httpMethod = "POST")
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
    @DeleteMapping("/{lessonId}/{teacherId}")
    public void deleteTL(@PathVariable @ApiParam(value = "课程号") long lessonId, @PathVariable @ApiParam(value = "教师账号") long teacherId) {
        tlService.deleteTL(lessonId, teacherId);
    }

}
