package com.example.demo.controller;

import com.example.demo.entity.TeacherEntity;
import com.example.demo.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @ Author     ：Theory
 * @ Description：教师控制类
 */

@Api(value = "TeacherController|教师控制器")
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    /**
      * @Author      : QinYingran
      * @Description : 获取所有教师
      * @return      : 教师列表
      */
    @ApiOperation(value = "获取所有教师", notes = "获取所有教师",httpMethod = "GET")
    @GetMapping("")
    public List<TeacherEntity> getAllTeacher() {
        return  teacherService.getAll();
    }


//    @ApiOperation(value = "获取所有教师并去重", notes = "获取所有教师并去重",httpMethod = "GET")
//    @GetMapping("/view")
//    public List<Object> getAllDistinctly() {
//        return  teacherService.getAllDistinctly();
//    }

    /**
      * @Author      : QinYingran
      * @Description : 向数据库插入教师
      * @Param       : [teacherEntity]
      * @return      : void
      */
    @ApiOperation(value = "向数据库插入教师", notes = "向数据库插入教师",httpMethod = "POST")
    @ApiParam(name = "teacherEntity",value = "教师实体,其中teacherId不能为空")
    @PostMapping("")
    public long insertTeacher(@RequestBody TeacherEntity teacherEntity) {
        return teacherService.insertTeacher(teacherEntity);
    }

    /**
      * @Author      : QinYingran
      * @Description : 更新教师
      * @Param       : [teacherEntity]
      * @return      : void
      */
    @ApiOperation(value = "更新教师", notes = "更新教师",httpMethod = "PUT")
    @ApiParam(name = "teacherEntity",value = "教师实体,其中teacherId不能为空")
    @PutMapping("")
    public void updateTeacher(@RequestBody TeacherEntity teacherEntity) {
        teacherService.insertTeacher(teacherEntity);
    }

    /**
      * @Author      : QinYingran
      * @Description : 根据教师id删除教师
      * @Param       : [teacherId]
      * @return      : void
      */
    @PreAuthorize("hasRole('lessonManager')")
    @ApiOperation(value = "根据教师id删除教师", notes = "根据教师id删除教师",httpMethod = "DELETE")
    @ApiParam(name = "teacherId",value = "教师账号")
    @DeleteMapping("")
    public void deleteTeacher(@RequestParam("teacherId") String teacherId) {
        teacherService.deleteTeacher(teacherId);
    }


    /**
      * @Author      : Theory
      * @Description : 根据关键字查询老师
      * @Param       : keyword -- 关键字
      * @return      : java.util.List<com.example.demo.entity.TeacherEntity>
      */
    @ApiOperation(value = "根据关键词查询老师", notes = "根据关键词查询老师",httpMethod = "GET")
    @GetMapping("/keyword")
    public List<Object> getTeacherByKeyword(@RequestParam String keyword){
        return teacherService.getTeacherByKeyword(keyword);
    }



    /**
      * @Author      : Theory
      * @Description : 根据id查询教师
      * @Param       : [id] -- 教师id
      * @return      : 相关id的教师
      */
    @ApiOperation(value = "根据id查询老师", notes = "根据id查询老师",httpMethod = "GET")
    @GetMapping("/id")
    public TeacherEntity getTeacherById(@RequestParam String id){
        return teacherService.getTeacherById(id);
    }


    /**
      * @Author      : Theory
      * @Description : 通过学校名返回该学校教师列表
      * @Param       : [schoolName] -- 学校名
      * @return      : 老师列表
      */
    @PreAuthorize("hasRole('lessonManager')")
    @ApiOperation(value = "根据学校名查询老师", notes = "根据学校名查询老师",httpMethod = "GET")
    @GetMapping("/schoolName")
    public List<TeacherEntity> getTeacherBySchool(@RequestParam(value = "schoolName") String schoolName){
        return teacherService.getTeacherBySchool(schoolName);
    }


    /**
      * @Author      : Theory
      * @Description : 上传教师图
      * @Param       : [file, fileName] -- 图片和文件名
      */
    @ApiOperation(value = "上传教师图片",notes = "上传教师图片")
    @PostMapping("/imgUpload")
    public void uploadImg(@RequestParam("img")@ApiParam(value = "img") MultipartFile file,
                          @RequestParam("fileName")@ApiParam(value = "fileName") String fileName){
        try {
//            File path2 = new File(ResourceUtils.getURL("classpath:static").getPath().replace("%20"," ").replace('/', '\\'));
//            if(!path2.exists()) path2 = new File("");
//            //如果上传目录为/static/img/teacher/，则可以如下获取：
//            File upload2 = new File(path2.getAbsolutePath(),"img/teacher/");
//            if(!upload2.exists()) upload2.mkdirs();
//            String path=upload2.getAbsolutePath()+"/"+fileName;
//            File img = new File(path);
//            if(!img.exists())
//                img.createNewFile();//不存在则创建新文件
//            file.transferTo(img);
//            TeacherEntity oldTeacher = teacherService.getTeacherById(fileName);
//            oldTeacher.setImgLink("../img/teacher/"+fileName);
//            teacherService.insertTeacher(oldTeacher);

            String UPLOAD_PATH = "File/img/teacher";
            InputStream inputStream = file.getInputStream();
            Path directory = Paths.get(UPLOAD_PATH);
            if(!Files.exists(directory)){
                Files.createDirectories(directory);
            }
            Files.copy(inputStream, directory.resolve(fileName));
            TeacherEntity oldTeacher = teacherService.getTeacherById(fileName);
            oldTeacher.setImgLink("../File/img/teacher/"+fileName);
            teacherService.insertTeacher(oldTeacher);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
