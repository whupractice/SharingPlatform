package com.example.demo.controller;

import com.example.demo.entity.SLEntity;
import com.example.demo.service.SLService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ Author     ：Theory
 * @ Description：学生选课控制类
 */

@Api(value = "SLController|学生选课控制器")
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
    @ApiOperation(value = "根据学生账号获取所选课程", notes = "根据学生账号获取所选课程",httpMethod = "GET")
    @ApiParam(name = "stuId",value = "学生账号")
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
    @ApiOperation(value = "通过课程号获取选择这门课程的学生数量", notes = "通过课程号获取选择这门课程的学生数量",httpMethod = "GET")
    @ApiParam(name = "lessonId",value = "课程号")
    @GetMapping("")
    public int getStuNumByLessonId(@RequestParam long lessonId){
        return slService.getStuNumByLessonId(lessonId);
    }


    /**
      * @Author      : Theory
      * @Description : 向数据库中插入学生选课记录
      * @Param       : [sl] -- 学生选课记录
      */
    @ApiOperation(value = "向数据库中插入学生选课记录", notes = "向数据库中插入学生选课记录",httpMethod = "POST")
    @ApiParam(name = "sl",value = "学生选课实体，其中studentId、lessonId、praiseNum、star不能为空")
    @PostMapping("")
    public void insertSL(@RequestBody SLEntity sl){
        slService.insertSL(sl);
    }



    /**
      * @Author      : Theory
      * @Description : 更新数据库中学生选课记录
      * @Param       : [sl] -- 新的学生选课记录
      */
    @ApiOperation(value = "更新数据库中学生选课记录", notes = "更新数据库中学生选课记录",httpMethod = "PUT")
    @ApiParam(name = "sl",value = "学生选课实体，其中studentId、lessonId、praiseNum、star不能为空")
    @PutMapping("")
    public void updateSL(@RequestBody SLEntity sl){slService.insertSL(sl);}


    /**
     * @Author      : Theory
     * @Description : 通过学生账号和课程账号删除选课记录
     * @Param       : [stuId,lessonId] -- 学生账号、课程账号
     */
    @ApiOperation(value = "通过学生账号和课程账号删除选课记录", notes = "通过学生账号和课程账号删除选课记录",httpMethod = "DELETE")
    @ApiParam(name = "stuId",value = "学生账号")
    @DeleteMapping("/{stuId}/{lessonId}")
    public void deleteSL(@PathVariable @ApiParam(value = "学生账号") long stuId ,
                         @PathVariable @ApiParam(value = "课程账号") long lessonId){
        slService.deleteSL(stuId,lessonId);
    }

}
