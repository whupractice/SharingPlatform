package com.example.demo.service;

import com.example.demo.entity.LessonEntity;
import com.example.demo.entity.SLEntity;
import com.example.demo.entity.StudentEntity;
import com.example.demo.keys.SLKeys;
import com.example.demo.repository.LessonRepository;
import com.example.demo.repository.SLRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ Author     ：Theory
 * @ Description：学生选课逻辑服务类
 */


@Service
public class SLService {

    @Autowired
    SLRepository slRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    LessonRepository lessonRepository;


    public List<SLEntity> getAll() {
        return slRepository.findAll();
    }

    /**
      * @Author      : Theory
      * @Description : 根据学生号获取学生选课列表
      * @Param       : [stuId] -- 学生账号
      * @return      : 返回该学生的学生选课信息
      */
    public List<SLEntity> getSLByStuId(String stuId){
        long newId = Long.parseLong(stuId);
        return slRepository.getSLByStuId(newId);
    }

    /**
      * @Author      : QinYingran
      * @Description : 根据课程号获取学生选课列表
      * @Param       : [lessonId]
      * @return      : 返回该课程的学生选课信息
      */
    public List<SLEntity> getSLByLessonId(String lessonId) {
        long newId = Long.parseLong(lessonId);
        return slRepository.getSLByLessonId(newId);
    }

    /**
      * @Author      : QinYingran
      * @Description : 根据学生号获取课程列表
      * @Param       : [stuId]
      * @return      : java.util.List<com.example.demo.entity.LessonEntity>
      */
    public List<LessonEntity> getLessonByStuId(String stuId) {
        long newId = Long.parseLong(stuId);
        List<LessonEntity> lessonEntities = new ArrayList<>();
        for(SLEntity slEntity : slRepository.getSLByStuId(newId)) {
            lessonEntities.add(lessonRepository.getByLessonId(slEntity.getLessonId()));
        }
        return lessonEntities;
    }

    /**
      * @Author      : QinYingran
      * @Description : 根据课程号获取学生列表
      * @Param       : [lessonId]
      * @return      : java.util.List<com.example.demo.entity.StudentEntity>
      */
    public List<StudentEntity> getStudentByLessonId(String lessonId) {
        long newId = Long.parseLong(lessonId);
        List<StudentEntity> studentEntities = new ArrayList<>();
        for(SLEntity slEntity : slRepository.getSLByLessonId(newId)) {
            studentEntities.add(studentRepository.getStuById(slEntity.getPhone()));
        }
        return studentEntities;
    }


    public List<SLEntity> getEvaluationByLessonId(String lessonId) {
        long newId = Long.parseLong(lessonId);
        return slRepository.getEvaluationByLessonId(newId);
    }


    /**
      * @Author      : Theory
      * @Description : 查询选了这门课的学生数量
      * @Param       : [lessonId] -- 课程号
      * @return      : 这门课的选课学生数量
      */
    public int getStuNumByLessonId(String lessonId){
        long newId = Long.parseLong(lessonId);
        return slRepository.getStuNumByLessonId(newId);
    }



    /**
      * @Author      : Theory
      * @Description : 插入学生选课记录
      * @Param       : [sl] -- 学生选课记录
      */
    public void insertSL(SLEntity sl){
        slRepository.save(sl);
    }



    /**
      * @Author      : Theory
      * @Description : 通过学生账号和课程账号删除选课记录
      * @Param       : [stuId,lessonId] -- 学生账号、课程账号
      */
    public void deleteSL(String stuId,String lessonId){
        long newId = Long.parseLong(stuId);
        long newId2 = Long.parseLong(lessonId);
        slRepository.deleteById(new SLKeys(newId,newId2));
    }
    
}
