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
      * @Description : 获取所有教师
      * @Param       : []
      * @return      : 所有教师列表
      */
    public List<Object> getAllDistinctly() {
        return teacherRepository.getAllDistinctly();
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



    /**
      * @Author      : Theory
      * @Description : 根据关键字查询老师
      * @Param       : [keyword] -- 教师姓名关键字
      * @return      : 包含此关键字的教师
      */
    public List<TeacherEntity> getTeacherByKeyword(String keyword){
        keyword = "%"+keyword+"%";
        return teacherRepository.getTeacherByKeyword(keyword);
    }
}
