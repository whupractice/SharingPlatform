package com.example.demo.service;

import com.example.demo.entity.TLEntity;
import com.example.demo.keys.TLKeys;
import com.example.demo.repository.TLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author     ：Theory
 * @ Description：院校教师逻辑服务类
 */

@Service
public class TLService {

    @Autowired
    TLRepository tlRepository;

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
    public void deleteTL(long teacherId,long lessonId) {
        tlRepository.deleteById(new TLKeys(teacherId,lessonId));
    }
}
