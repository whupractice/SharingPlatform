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
    public long insertTeacher(TeacherEntity teacherEntity) {
        teacherRepository.save(teacherEntity);
        return teacherRepository.getNewTeacherId();
    }

    /**
      * @Author      : QinYingran
      * @Description : 根据教师id删除教师
      * @Param       :
      * @return      :
      */
    public void deleteTeacher(String id) {
        long newId = Long.parseLong(id);
        teacherRepository.deleteById(newId);
    }



    /**
      * @Author      : Theory
      * @Description : 根据关键字查询老师
      * @Param       : [keyword] -- 教师姓名关键字
      * @return      : 包含此关键字的教师
      */
    public List<Object> getTeacherByKeyword(String keyword){
        keyword = "%"+keyword+"%";
        return teacherRepository.getTeacherByKeyword(keyword);
    }



    public TeacherEntity getTeacherById(String id){
        long newId = Long.parseLong(id);//转换类型
        return teacherRepository.getTeacherById(newId);
    }


    public List<TeacherEntity> getTeacherBySchool(String schoolName){
        List<TeacherEntity> ts = teacherRepository.getTeacherBySchool(schoolName);
        if(ts.size()==0)
            return null;
        else
            return ts;
    }
}
