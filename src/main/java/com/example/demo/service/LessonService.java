package com.example.demo.service;

import com.example.demo.baseClass.Lesson;
import com.example.demo.entity.LessonEntity;
import com.example.demo.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ Author     ：Theory
 * @ Description：课程逻辑服务类
 */

@Service
public class LessonService {

    @Autowired
    LessonRepository lessonRepository;


    /**
      * @Author      : Theory
      * @Description : 获得所有课程
      * @return      : 课程列表
      */
    public List<LessonEntity> getAll(){
        return lessonRepository.findAll();
    }

    /**
      * @Author      : QinYingran
      * @Description : 分页条件查询获得所有课程
      * @Param       : [specification, pageable]
      * @return      : org.springframework.data.domain.Page<com.example.demo.entity.LessonEntity>
      */
    public Page<LessonEntity> getAll(Specification<LessonEntity> specification, Pageable pageable) {
        return lessonRepository.findAll(specification,pageable);
    }

    /**
      * @Author      : QinYingran
      * @Description : 根据课程id获取课程列表
      * @Param       : [schoolId]
      * @return      : java.util.List<com.example.demo.entity.LessonEntity>
      */
    public LessonEntity getByLessonId(String lessonId) {
        long newId = Long.parseLong(lessonId);
        return lessonRepository.getByLessonId(newId);
    }

    /**
      * @Author      : QinYingran
      * @Description : 获得所有课程并去重
      * @Param       : []
      * @return      : 课程列表
      */
    public List<Object> getAllDistinctly() {
        return lessonRepository.getAllDistinctly();
    }

    /**
      * @Author      : Theory
      * @Description : 获取精品课程
      * @return      : 精品课程列表
      */
    public List<LessonEntity> getExcellentLesson(){
        return lessonRepository.getExcellentClass();
    }



    /**
      * @Author      : Theory
      * @Description : 获取热门课程
      * @return      : 一定数量的热门课程
      * TODO 查询有点慢，希望优化查询算法
      */
    public List<LessonEntity> getHotLesson(int num){
        Sort sort = new Sort(Sort.Direction.DESC, "welcome");
        List<LessonEntity> primList = lessonRepository.findAll(sort);//按照热度从高到低排序
        List<LessonEntity> finalList = new ArrayList<>();//最终的热门课程列表
        int n = (num >= primList.size())? primList.size() : num;
        /*选择n个最热门课程*/
        for(int i = 0;i < n;i++) {
            finalList.add(primList.get(i));
        }
        return finalList;
    }

    /**
      * @Author      : QinYingran
      * @Description : 根据学校名查询课程
      * @Param       : [schoolName]
      * @return      : java.util.List<com.example.demo.entity.LessonEntity>
      */
    public List<LessonEntity> getBySchoolName(String schoolName) {
        return lessonRepository.getBySchoolName(schoolName);
    }

    /**
      * @Author      : QinYingran
      * @Description : 根据关键词查询课程
      * @Param       : [keyWord]
      * @return      : 含有关键词的课程列表
      */
    public List<Object> getLessonByKeyword(String keyword) {
        keyword = "%"+keyword+"%";
        return lessonRepository.getLessonByKeyword(keyword);
    }

    /**
      * @Author      : QinYingran
      * @Description : 根据状态查询课程
      * @Param       : [status]
      * @return      : java.util.List<com.example.demo.entity.LessonEntity>
      */
    public List<LessonEntity> getByStatus(String status) {
        return lessonRepository.getByStatus(status);
    }

    /**
      * @Author      : QinYingran
      * @Description : 根据学科查询课程
      * @Param       : [subject]
      * @return      : java.util.List<com.example.demo.entity.LessonEntity>
      */
    public List<LessonEntity> getBySubject(String subject) {
        return lessonRepository.getBySubject(subject);
    }

    /**
      * @Author      : QinYingran
      * @Description : 根据状态和学科查询课程
      * @Param       : [status, subject]
      * @return      : java.util.List<com.example.demo.entity.LessonEntity>
      */
    public List<LessonEntity> getByStatusAndSubject(String status,String subject) {
        return lessonRepository.getByStatusAndSubject(status,subject);
    }



    //根据学校和学院查询课程
    public List<LessonEntity> getLessonsBySchoolAndAcademy(String sName,String aName){
        return lessonRepository.getLessonsBySchoolAndAcademy(sName,aName);
    }


    /**
      * @Author      : Theory
      * @Description : 向数据库中插入课程
      * @Param       : [lesson]
      */
    public void insertLesson(LessonEntity lesson){
        lessonRepository.save(lesson);
    }



    /**
      * @Author      : Theory
      * @Description : 根据课程id删除课程
      * @Param       : [lessonId] -- 课程id号
      * @return      : void
      */
    public void deleteLesson(long lessonId){
        lessonRepository.deleteById(lessonId);
    }



    /**
      * @Author      : Theory
      * @Description : 根据id返回课程
      * @Param       : [id]--课程号
      * @return      : 课程
      */
    public LessonEntity getLessonById(String id){
        long lessonId = Long.parseLong(id);
        return lessonRepository.findById(lessonId).get();
    }
}
