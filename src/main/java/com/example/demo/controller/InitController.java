package com.example.demo.controller;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Author     ：Theory
 * @ Description：初始化数据库的控制类
 */

//爬虫
@Api(value = "InitController|数据库初始化控制器")
@RestController
@RequestMapping("/init")
public class InitController {


//    @Autowired
//    InitService initService;
//
//
//    /**
//      * @Author      : Theory
//      * @Description : 爬虫初始化数据库
//      */
//    @PreAuthorize("hasRole('manager')")
//    @ApiOperation(value = "初始化数据库", notes = "初始化数据库",httpMethod = "GET")
//    @GetMapping("")
//    public void initDB(){
//        initService.insertT_L_TL();
//    }
//
//    @PreAuthorize("hasRole('manager')")
//    @ApiOperation(value = "初始化数据库2", notes = "初始化数据库2",httpMethod = "GET")
//    @GetMapping("/2")
//    public void initDB2(){
//        initService.insertSchoolInfo();
//    }


}
