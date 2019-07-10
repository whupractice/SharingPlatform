package com.example.demo.controller;

import com.example.demo.entity.AcademyEntity;
import com.example.demo.service.AcademyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ Author     ：QinYingran
 * @ Description：学院控制类
 */
@Api(value = "AcademyController|学院控制器")
@RestController
@RequestMapping("/academy")
public class AcademyController {
    @Autowired
    AcademyService academyService;


    @ApiOperation(value = "获取所有学院", notes = "获取所有学院",httpMethod = "GET")
    @GetMapping("")
    public List<AcademyEntity> getAll() {
        return academyService.getAll();
    }

    @ApiOperation(value = "根据学院名获取学院列表", notes = "根据学院名获取学院列表",httpMethod = "GET")
    @ApiParam(name = "academyName",value = "学院名")
    @GetMapping("/academyName")
    public List<AcademyEntity> getByAcademyName(@RequestParam String academyName) {
        return academyService.getByAcademyName(academyName);
    }

    @PreAuthorize("hasRole('lessonManager')")
    @ApiOperation(value = "根据学校名获取学院列表", notes = "根据学校名获取学院列表",httpMethod = "GET")
    @ApiParam(name = "/schoolName",value = "学校名")
    @GetMapping("/schoolName")
    public List<AcademyEntity> getBySchoolName(@RequestParam String schoolName) {
        return academyService.getBySchoolName(schoolName);
    }


    @ApiOperation(value = "根据学校名和学院名获取学院列表", notes = "根据学校名和学院名获取学院列表",httpMethod = "GET")
    @GetMapping("/schoolNameAndAcademyName")
    public List<AcademyEntity> getBySchoolNameAndAcademyName(@RequestParam @ApiParam(name = "schoolName",value = "学校名") String schoolName,
                                                             @RequestParam @ApiParam(name = "academyName",value = "学院名") String academyName) {
        return academyService.getBySchoolNameAndAcademyName(schoolName, academyName);
    }

    @PreAuthorize("hasRole('manager')")
    @ApiOperation(value = "向数据库中插入学院", notes = "向数据库中插入学院",httpMethod = "POST")
    @ApiParam(name = "academyEntity",value = "学院实体,其中任意属性都不能为空")
    @PostMapping("")
    public boolean insertAcademy(@RequestBody AcademyEntity academyEntity) {
        return academyService.insertAcademy(academyEntity);
    }

    @PreAuthorize("hasRole('manager')")
    @ApiOperation(value = "根据学院id删除学院", notes = "根据学院id删除学院",httpMethod = "DELETE")
    @ApiParam(name = "id",value = "学院id")
    @DeleteMapping("")
    public void deleteByAcademyId(@RequestParam String id) {
        academyService.deleteByAcademyId(id);
    }
}
