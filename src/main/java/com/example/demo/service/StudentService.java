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
      * @Description : 判断账号密码是否正确
      * @Param       : [user,id] -- 用户名、密码
      * @return      : 是否正确
      */
    public boolean judgeLogin(long user,String pwd){
        StudentEntity s =studentRepository.getStuById(user);
        if(s.getPwd().equals(pwd)){
            return true;
        }else {
            return false;
        }

    }


    /**
      * @Author      : Theory
      * @Description : 判断管理员是否账号和密码正确
      * @Param       : [user, pwd] -- 用户名、密码
      * @return      : boolean
      */
    public boolean judgeMLogin(long user,String pwd){
        StudentEntity s =studentRepository.getStuById(user);
        if(s.getPwd().equals(pwd) && s.getIsManager()==1){
            return true;
        }else {
            return false;
        }
    }



    public StudentEntity getStuById(String user){
        Long username = Long.parseLong(user);
        return studentRepository.getStuById(username);
    }


    /**
      * @Author      : Theory
      * @Description : 注册账号，返回账号
      * @Param       : [pwd]
      * @return      : 账号
      */
    public long register(StudentEntity stu){
        if(studentRepository.getStuByNickName(stu.getNickName()).size()!=0){
            return -1;//此昵称已被用
        }
        studentRepository.save(stu);//向数据库中插入学生
        return studentRepository.getMaxId();//获取最大的账号（新注册的）
    }



    /**
      * @Author      : Theory
      * @Description : 获取数据库中所有学生
      * @return      : 所有学生
      */
    public List<StudentEntity> getAllStudent(){
        return studentRepository.findAll();
    }



    /**
      * @Author      : Theory
      * @Description : 插入学生记录
      * @Param       : [stu] -- 学生
      */
    public void insertStudent(StudentEntity stu){
        studentRepository.save(stu);
    }


    /**
      * @Author      : Theory
      * @Description : 根据学生账号删除学生id
      * @Param       : [id] -- 学生账号
      */
    public void deleteStudent(long id){
        studentRepository.deleteById(id);
    }

}
