package com.example.demo.controller;

import com.example.demo.entity.LessonEntity;
import com.example.demo.service.LessonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
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


    @ApiOperation(value = "根据课程id获取课程列表", notes = "根据课程id获取课程列表",httpMethod = "GET")
    @ApiParam(name = "schoolId",value = "课程id")
    @GetMapping("/id")
    public LessonEntity getByLessonId(@RequestParam String schoolId) {
        return lessonService.getByLessonId(schoolId);
    }


//    @ApiOperation(value = "获取所有课程并去重", notes = "获取所有课程并去重",httpMethod = "GET")
//    @GetMapping("/view")
//    public List<Object> getAllDistinctly(){
//        return lessonService.getAllDistinctly();
//    }

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

    @ApiOperation(value = "根据学校名查询课程", notes = "根据学校名查询课程",httpMethod = "GET")
    @GetMapping("/schoolName")
    @ApiParam(name = "schoolName",value = "学校名")
    public List<LessonEntity> getBySchoolName(@RequestParam String schoolName) {
        return lessonService.getBySchoolName(schoolName);
    }

    @ApiOperation(value = "根据关键词查询课程", notes = "根据关键词查询课程（课程名中的）",httpMethod = "GET")
    @ApiParam(name = "keyword",value = "课程名关键词")
    @GetMapping("/keyword")
    public List<Object> getLessonByKeyword(@RequestParam String keyword) {
        return lessonService.getLessonByKeyword(keyword);
    }


    @ApiOperation(value = "分页获取课程列表", notes = "分页获取课程列表",httpMethod = "GET")
    @GetMapping("/pages")
    public Page<LessonEntity> getLessonPages(@PageableDefault(size = 12, sort = {"welcome"}, direction = Sort.Direction.DESC)@ApiParam(value = "分页信息") Pageable pageable,
                                             @RequestParam(value = "status",required = false,defaultValue ="")@ApiParam(value = "课程状态") String status,
                                             @RequestParam(value = "subject",required = false,defaultValue ="")@ApiParam(value = "学科") String subject
                                             ) {
        Specification<LessonEntity> specification = createSpecification(status,subject);
        return lessonService.getAll(specification,pageable);
    }

    @ApiOperation(value = "根据学校名分页获取课程列表", notes = "根据学校名分页获取课程列表",httpMethod = "GET")
    @GetMapping("/pagesBySchoolName")
    public Page<LessonEntity> getLessonPages(@PageableDefault(size = 12, sort = {"welcome"}, direction = Sort.Direction.DESC)@ApiParam(value = "分页信息") Pageable pageable,
                                             @RequestParam(value = "schoolName",required = false,defaultValue ="")@ApiParam(value = "学校名") String schoolName
    ) {
        Specification<LessonEntity> specification = createSpecification(schoolName);
        return lessonService.getAll(specification,pageable);
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


    public Specification<LessonEntity> createSpecification(String status,String subject) {

        return (Specification<LessonEntity>) (root, query, cb) -> {
            //用于暂时存放查询条件的集合
            List<Predicate> predicatesList = new ArrayList<>();

            if (!status.equals("全部")) {
                Predicate predicate = cb.like(root.get("status"), "%" + status + "%");
                predicatesList.add(predicate);
            }
            if (!subject.equals("全部")) {
                Predicate predicate = cb.like(root.get("subject"), "%" + subject + "%");
                predicatesList.add(predicate);
            }
            Predicate[] predicates = new Predicate[predicatesList.size()];
            return cb.and(predicatesList.toArray(predicates));
        };

    }

    public Specification<LessonEntity> createSpecification(String schoolName) {

        return (Specification<LessonEntity>) (root, query, cb) -> {
            //用于暂时存放查询条件的集合
            List<Predicate> predicatesList = new ArrayList<>();


            Predicate predicate = cb.like(root.get("schoolName"), "%" + schoolName + "%");
            predicatesList.add(predicate);

            Predicate[] predicates = new Predicate[predicatesList.size()];
            return cb.and(predicatesList.toArray(predicates));
        };

    }


}
