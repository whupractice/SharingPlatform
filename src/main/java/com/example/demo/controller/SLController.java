package com.example.demo.controller;

import com.example.demo.entity.LessonEntity;
import com.example.demo.entity.SLEntity;
import com.example.demo.entity.StudentEntity;
import com.example.demo.service.SLService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @ApiOperation(value = "获取所有学生选课信息", notes = "获取所有学生选课信息",httpMethod = "GET")
    @GetMapping("")
    public List<SLEntity> getAllSL() {
        return slService.getAll();
    }

    /**
      * @Author      : Theory
      * @Description : 根据学生账号获取所选课程
      * @Param       : [studId] -- 学生账号
      * @return      : 选课记录list
      */
    @ApiOperation(value = "根据学生id获取学生选课信息", notes = "根据学生id获取学生选课信息",httpMethod = "GET")
    @ApiParam(name = "stuId",value = "学生id")
    @GetMapping("/stuId")
    public List<SLEntity> getSLByStuId(@RequestParam String stuId){
        return slService.getSLByStuId(stuId);
    }

    //不加限制
    @ApiOperation(value = "根据课程id获取学生选课信息", notes = "根据课程id获取学生选课信息",httpMethod = "GET")
    @ApiParam(name = "lessonId",value = "课程id")
    @GetMapping("/lessonId")
    public List<SLEntity> getSLByLessonId(@RequestParam String lessonId){
        return slService.getSLByLessonId(lessonId);
    }

    @PreAuthorize("hasRole('student')")
    @ApiOperation(value = "根据学生id获取课程信息", notes = "根据学生id获取课程信息",httpMethod = "GET")
    @ApiParam(name = "stuId",value = "学生id")
    @GetMapping("/getLessonByStuId")
    public List<LessonEntity> getLessonByStuId(@RequestParam String stuId) {
        return slService.getLessonByStuId(stuId);
    }

    @PreAuthorize("hasRole('student')")
    @ApiOperation(value = "分页根据学生id获取课程信息", notes = "分页根据学生id获取课程信息",httpMethod = "GET")

    @GetMapping("/getLessonPagesByStuId")
    public List<LessonEntity> getLessonPagesByStuId(@RequestParam @ApiParam(value = "学生id") String stuId,
                                                    @RequestParam @ApiParam(value = "第几页") String page,
                                                    @RequestParam @ApiParam(value = "每页有几个") String num) {
        return slService.getLessonPagesByStuId(stuId,page,num);
    }


    public Object getLessonPagesByStuIdPagesNum(@RequestParam @ApiParam(value = "学生id") String stuId,
                                                @RequestParam @ApiParam(value = "学生id") String num) {
        return slService.getLessonPagesByStuIdPagesNum(stuId,num);
    }


    @PreAuthorize("hasRole('lessonManager')")
    @ApiOperation(value = "根据课程id获取学生信息", notes = "根据课程id获取学生信息",httpMethod = "GET")
    @ApiParam(name = "lessonId",value = "课程id")
    @GetMapping("/getStudentByLessonId")
    public List<StudentEntity> getStudentByLessonId(@RequestParam String lessonId) {
        return slService.getStudentByLessonId(lessonId);
    }

    @ApiOperation(value = "根据课程id获取评论信息", notes = "根据课程id获取评论信息",httpMethod = "GET")
    @ApiParam(name = "lessonId",value = "课程id")
    @GetMapping("/getEvaluationByLessonId")
    public List<SLEntity> getEvaluationByLessonId(@RequestParam String lessonId) {
        return slService.getEvaluationByLessonId(lessonId);
    }


    /**
      * @Author      : Theory
      * @Description : 通过课程号获取选择这门课程的学生数量
      * @Param       : [lessonId] -- 课程id
      * @return      : 选这门课的学生数量
      */
    @ApiOperation(value = "根据课程号获取选择这门课程的学生数量", notes = "根据课程号获取选择这门课程的学生数量",httpMethod = "GET")
    @ApiParam(name = "lessonId",value = "课程号")
    @GetMapping("/getStuNumByLessonId")
    public int getStuNumByLessonId(@RequestParam String lessonId){
        return slService.getStuNumByLessonId(lessonId);
    }


    /**
      * @Author      : Theory
      * @Description : 向数据库中插入学生选课记录
      * @Param       : [sl] -- 学生选课记录
      */
    @PreAuthorize("hasRole('student')")
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
    @PreAuthorize("hasRole('student')")
    @ApiOperation(value = "更新数据库中学生选课记录", notes = "更新数据库中学生选课记录",httpMethod = "PUT")
    @ApiParam(name = "sl",value = "学生选课实体，其中studentId、lessonId、praiseNum、star不能为空")
    @PutMapping("")
    public void updateSL(@RequestBody SLEntity sl){slService.insertSL(sl);}


    /**
     * @Author      : Theory
     * @Description : 通过学生账号和课程账号删除选课记录
     * @Param       : [stuId,lessonId] -- 学生账号、课程账号
     */
    @PreAuthorize("hasRole('student')")
    @ApiOperation(value = "根据学生账号和课程账号删除选课记录", notes = "根据学生账号和课程账号删除选课记录",httpMethod = "DELETE")
    @DeleteMapping("/stuId/lessonId")
    public void deleteSL(@RequestParam @ApiParam(value = "学生账号") String stuId ,
                         @RequestParam @ApiParam(value = "课程账号") String lessonId){
        slService.deleteSL(stuId,lessonId);
    }

}
