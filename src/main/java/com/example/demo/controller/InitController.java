package com.example.demo.controller;


import com.example.demo.service.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Author     ：Theory
 * @ Description：初始化数据库的控制类
 */


@RestController
@RequestMapping("/init")
public class InitController {


    @Autowired
    InitService initService;


    /**
      * @Author      : Theory
      * @Description : 爬虫初始化数据库
      */
    @GetMapping("")
    public void initDB(){
        initService.insertT_L_TL();
    }


}
