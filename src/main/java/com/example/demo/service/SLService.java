package com.example.demo.service;

import com.example.demo.entity.SLEntity;
import com.example.demo.keys.SLKeys;
import com.example.demo.repository.SLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author     ：Theory
 * @ Description：学生选课逻辑服务类
 */


@Service
public class SLService {

    @Autowired
    SLRepository slRepository;


    /**
      * @Author      : Theory
      * @Description : 通过学生账号获取学生所选课程号
      * @Param       : [stuId] -- 学生账号
      * @return      : 返回该学生的选课信息
      */
    public List<SLEntity> getSLByStuId(long stuId){
        return slRepository.getAllByStuId(stuId);
    }

    public List<SLEntity> getSLByLessonId(long lessonId) {
        return slRepository.getSLByLessonId(lessonId);
    }


    /**
      * @Author      : Theory
      * @Description : 查询选了这门课的学生数量
      * @Param       : [lessonId] -- 课程号
      * @return      : 这门课的选课学生数量
      */
    public int getStuNumByLessonId(long lessonId){
        return slRepository.getStuNumByLessonId(lessonId);
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
    public void deleteSL(long stuId,long lessonId){
        slRepository.deleteById(new SLKeys(stuId,lessonId));
    }
    
}
