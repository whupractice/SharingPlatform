package com.example.demo.service;
import com.example.demo.entity.LessonEntity;
import com.example.demo.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

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
      * @return      : java.util.List<com.example.demo.entity.LessonEntity>
      */
    public List<LessonEntity> getAll(){
        return lessonRepository.findAll();
    }



    /**
      * @Author      : Theory
      * @Description : 获取精品课程
      * @return      : java.util.List<com.example.demo.entity.LessonEntity>
      */
    public List<LessonEntity> getExcellentLesson(){
        return lessonRepository.getExcellentClass();
    }



    /**
      * @Author      : Theory
      * @Description : 获取热门课程
      * @return      : java.util.List<com.example.demo.entity.LessonEntity>
      */
    public List<LessonEntity> getHotClass(int num){
        Sort sort = new Sort(Sort.Direction.DESC, "welcome");
        List<LessonEntity> primList = lessonRepository.findAll(sort);//按照热度从高到低排序
        List<LessonEntity> finalList = new ArrayList<>();//最终的热门课程列表
        int n = (num >= primList.size())? 8 : primList.size();
        /*选择n个最热门课程*/
        for(int i = 0;i < n;i++) {
            finalList.add(primList.get(i));
        }
        return finalList;
    }
}
