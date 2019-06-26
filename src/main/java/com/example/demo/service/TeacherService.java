package com.example.demo.service;

import com.example.demo.entity.TeacherEntity;
import com.example.demo.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author     ：Theory
 * @ Description：教师逻辑服务类
 */

@Service
public class TeacherService {

    @Autowired
    TeacherRepository teacherRepository;

    /**
      * @Author      : QinYingran
      * @Description : 获得所有教师
      * @Param       :
      * @return      :
      */
    public List<TeacherEntity> getAll() {
        return teacherRepository.findAll();
    }

    /**
      * @Author      : QinYingran
      * @Description :
      * @Param       : 向数据库插入教师
      * @return      :
      */
    public void insertTeacher(TeacherEntity teacherEntity) {
        teacherRepository.save(teacherEntity);
    }

    /**
      * @Author      : QinYingran
      * @Description : 根据教师id删除教师
      * @Param       :
      * @return      :
      */
    public void deleteTeacher(long id) {
        teacherRepository.deleteById(id);
    }
}
