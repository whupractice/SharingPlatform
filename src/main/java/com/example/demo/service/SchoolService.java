package com.example.demo.service;

import com.example.demo.entity.SchoolEntity;
import com.example.demo.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author     ：Theory
 * @ Description：院校服务逻辑服务类
 */

@Service
public class SchoolService {

    @Autowired
    SchoolRepository schoolRepository;

    /**
      * @Author      : QinYingran
      * @Description :获取所有学校列表
      * @return      : 所有学校列表
      */
    public List<SchoolEntity> getAll() {
        return schoolRepository.findAll();
    }

    /**
      * @Author      : QinYingran
      * @Description : 获取所有学校列表2
      * @Param       : []
      * @return      : 学校列表
      */
    public List<Object> getAllDistinctly() {
        return schoolRepository.getAllDistinctly();
    }

    /**
      * @Author      : QinYingran
      * @Description : 根据id获取学校介绍
      * @Param       : 学校id
      * @return      : 特定学校的介绍
      */
    public String getSchoolInfoById (Long id) {
        return schoolRepository.getSchoolInfoById(id);
    }

    /**
      * @Author      : QinYingran
      * @Description : 插入学校
      * @Param       :
      * @return      :
      */
    public void insertSchool(SchoolEntity school) {
        schoolRepository.save(school);
    }

    /**
      * @Author      : QinYingran
      * @Description : 根据学校id删除学校
      * @Param       :
      * @return      :
      */
    public void deleteSchool(long schoolId) {
        schoolRepository.deleteById(schoolId);
    }


}

