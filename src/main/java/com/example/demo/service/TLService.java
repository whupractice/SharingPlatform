package com.example.demo.service;

import com.example.demo.entity.LessonEntity;
import com.example.demo.entity.TLEntity;
import com.example.demo.entity.TeacherEntity;
import com.example.demo.keys.TLKeys;
import com.example.demo.repository.LessonRepository;
import com.example.demo.repository.TLRepository;
import com.example.demo.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ Author     ：Theory
 * @ Description：院校教师逻辑服务类
 */

@Service
public class TLService {

    @Autowired
    TLRepository tlRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    LessonRepository lessonRepository;


    public List<TLEntity> getAll() {
        return tlRepository.findAll();
    }

    /**
      * @Author      : QinYingran
      * @Description : 根据课程号获取教师授课列表
      * @Param       :
      * @return      :
      */
    public List<TLEntity> getTLByLessonId(String id) {
        long newId = Long.parseLong(id);
        return tlRepository.getAllByLessonId(newId);
    }

    /**
      * @Author      : QinYingran
      * @Description : 根据教师号获取教师授课列表
      * @Param       : [teacherId]
      * @return      : java.util.List<com.example.demo.entity.TLEntity>
      */
    public List<TLEntity> getAllByTeacherId(String teacherId) {
        long newId = Long.parseLong(teacherId);
        return tlRepository.getAllByTeacherId(newId);
    }

    /**
      * @Author      : QinYingran
      * @Description : 根据课程号获取教师列表
      * @Param       : [id]
      * @return      : java.util.List<com.example.demo.entity.TeacherEntity>
      */
    public List<TeacherEntity> getTeacherByLessonId(String id) {
        long newId = Long.parseLong(id);
        List<TeacherEntity> teacherEntities = new ArrayList<>();
        for (TLEntity tlEntity : tlRepository.getAllByLessonId(newId)) {
            teacherEntities.add(teacherRepository.getTeacherById(tlEntity.getTeacherId()));
        }
        return teacherEntities;
    }

    /**
      * @Author      : QinYingran
      * @Description : 根据教师号获取课程列表
      * @Param       : [teacherId]
      * @return      : java.util.List<com.example.demo.entity.LessonEntity>
      */
    public List<LessonEntity> getLessonByTeacherId(String teacherId) {
        long newId = Long.parseLong(teacherId);
        List<LessonEntity> lessonEntities = new ArrayList<>();
        for (TLEntity tlEntity : tlRepository.getAllByTeacherId(newId)) {
            lessonEntities.add(lessonRepository.getByLessonId(tlEntity.getLessonId()));
        }
        return lessonEntities;
    }


    /**
      * @Author      : QinYingran
      * @Description : 插入教师授课记录
      * @Param       :
      * @return      :
      */
    public void insertTL(TLEntity tl){
        tlRepository.save(tl);
    }

    /**
      * @Author      : QinYingran
      * @Description : 根据教师账号和课程账号删除授课记录
      * @Param       :
      * @return      :
      */
    public void deleteTL(String teacherId,String lessonId) {
        long newId = Long.parseLong(teacherId);
        long newId2 = Long.parseLong(lessonId);
        tlRepository.deleteById(new TLKeys(newId,newId2));
    }

    public void deleteTLByTeacherId(String teacherId) {
        List<TLEntity> tlEntities = getAllByTeacherId(teacherId);
        for(TLEntity tlEntity : tlEntities) {
            tlRepository.delete(tlEntity);
        }
    }
}
