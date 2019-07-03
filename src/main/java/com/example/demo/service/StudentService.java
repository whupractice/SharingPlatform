package com.example.demo.service;

import com.example.demo.entity.StudentEntity;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
      * @Param       : [phone,id] -- 用户名、密码
      * @return      : 是否正确
      */
    public StudentEntity judgeLogin(long phone,String pwd){
        StudentEntity s =studentRepository.getStuById(phone);
        if(s.getPwd().equals(pwd)){
            return s;
        }else {
            return null;
        }

    }



    /**
     * @Author      : QinYingran
     * @Description : 分页条件查询学生或管理员
     * @Param       : [specification, pageable]
     * @return      : org.springframework.data.domain.Page<com.example.demo.entity.StudentEntity>
     */
    public Page<StudentEntity> getAll(Specification<StudentEntity> specification, Pageable pageable) {
        return studentRepository.findAll(specification,pageable);
    }


    /**
      * @Author      : QinYingran
      * @Description : 根据id获取学生
      * @Param       : [phone]
      * @return      : com.example.demo.entity.StudentEntity
      */
    public StudentEntity getStuById(String phone){
        Long newPhone = Long.parseLong(phone);
        return studentRepository.getStuById(newPhone);
    }

    /**
      * @Author      : QinYingran
      * @Description : 根据nickName获取学生
      * @Param       : [nickName]
      * @return      : com.example.demo.entity.StudentEntity
      */
    public StudentEntity getStuByNickName(String nickName) {
        return studentRepository.getStuByNickName(nickName);
    }

    /**
      * @Author      : QinYingran
      * @Description : 根据schoolName获取学生
      * @Param       : [schoolName]
      * @return      : java.util.List<com.example.demo.entity.StudentEntity>
      */
    public List<StudentEntity> getStuBySchoolName(String schoolName) {
        return studentRepository.getStuBySchoolName(schoolName);
    }


    /**
      * @Author      : QinYingran
      * @Description : 注册学生
      * @Param       : [stu]
      * @return      : boolean
      */
    public boolean register(StudentEntity stu){
        stu.setNickName(String.valueOf(stu.getPhone()));
        StudentEntity stu1 = studentRepository.getStuById(stu.getPhone());
        StudentEntity stu2 = studentRepository.getStuByNickName(stu.getNickName());
        if(stu1==null && stu2==null) {
            studentRepository.save(stu);//向数据库中插入学生
            return true;
        }
        return false;
    }



    /**
      * @Author      : Theory
      * @Description : 获取数据库中所有学生
      * @return      : 所有学生
      */
    public List<StudentEntity> getAllStudent(){
        return studentRepository.findAll();
    }

    public List<StudentEntity> getAllManager() {
        return studentRepository.getAllManager();
    }



    /**
      * @Author      : QinYingran
      * @Description : 更新学生
      * @Param       : [stu]
      * @return      : boolean
      */
    public boolean insertStudent(StudentEntity stu){
        StudentEntity stu1 = studentRepository.getStuById(stu.getPhone());
        StudentEntity stu2 = studentRepository.getStuByNickName(stu.getNickName());
        if(stu1!=null) {
            if(stu2!=null && stu2.getNickName()!=stu1.getNickName()){
                return false;
            }
            studentRepository.save(stu);//向数据库中插入学生
            return true;
        }
        return false;
    }



    /**
      * @Author      : Theory
      * @Description : 根据学生账号删除学生id
      * @Param       : [id] -- 学生账号
      */
    public void deleteStudent(long phone){
        studentRepository.deleteById(phone);
    }

}
