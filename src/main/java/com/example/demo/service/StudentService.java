package com.example.demo.service;

import com.example.demo.entity.StudentEntity;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author     ：Theory
 * @ Description：学生逻辑服务类
 */

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;


    /**
      * @Author      : Theory
      * @Description : 通过账号获取学生密码
      * @Param       : [id]
      * @return      : java.lang.String
      */
    public boolean getPwdById(String user,String pwd){
        long username = Long.parseLong(user);
        String password = studentRepository.getPwdById(username);//调用数据库查询密码接口
        if(password.equals(pwd))
            return true;
        else
            return false;
    }

    /**
      * @Author      : Theory
      * @Description : 注册账号，返回账号
      * @Param       : [pwd]
      * @return      : long
      */
    public long register(StudentEntity stu){
        studentRepository.save(stu);//向数据库中插入学生
        return studentRepository.getMaxId();//获取最大的账号（新注册的）
    }




}
