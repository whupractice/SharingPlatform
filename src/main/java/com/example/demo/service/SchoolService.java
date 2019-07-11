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


    //根据姓名获取学校
    public SchoolEntity getSchoolByName(String name){
        List<SchoolEntity> schools = schoolRepository.getDuplicates(name);
        if(schools.size()!=0)
            return schools.get(0);

        return null;
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
      * @Description : 根据学校名中关键词获取学校列表
      * @Param       : [keyWord]
      * @return      : 学校列表
      */
    public List<SchoolEntity> getSchoolByKeyword(String keyword) {
        keyword = "%"+keyword+"%";
        return schoolRepository.getSchoolByKeyword(keyword);
    }

    /**
      * @Author      : QinYingran
      * @Description : 插入学校
      * @Param       :
      * @return      :
      */
    public boolean insertSchool(SchoolEntity school) {
        List<SchoolEntity>schools = schoolRepository.getSchoolByName(school.getSchoolName());
        if(schools.size()!=0)
            return false;
        else {
            schoolRepository.save(school);
            return true;
        }
    }


    //更新学校
    public void updateSchool(SchoolEntity schoolEntity){
        schoolRepository.save(schoolEntity);
    }




    /**
      * @Author      : QinYingran
      * @Description : 根据学校id删除学校
      * @Param       :
      * @return      :
      */
    public void deleteSchool(String schoolId) {
        long id = Long.parseLong(schoolId);
        schoolRepository.deleteById(id);
    }


    /**
      * @Author      : Theory
      * @Description : 根据id获取学校
      * @Param       : [id] -- 学校id
      * @return      : 获取到的学校
      */
    public SchoolEntity getSchoolById(String id){
        long schoolId = Long.parseLong(id);
        return schoolRepository.findById(schoolId).get();
    }

}

