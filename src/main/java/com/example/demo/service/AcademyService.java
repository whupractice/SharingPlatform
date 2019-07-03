package com.example.demo.service;

import com.example.demo.entity.AcademyEntity;
import com.example.demo.repository.AcademyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author     ：QinYingran
 * @ Description：学院逻辑服务类
 */
@Service
public class AcademyService {

    @Autowired
    AcademyRepository academyRepository;

    public List<AcademyEntity> getAll() {
        return academyRepository.findAll();
    }

    /**
      * @Author      : QinYingran
      * @Description : 根据学院名获取学院
      * @Param       : [academyName]
      * @return      : com.example.demo.entity.LessonEntity
      */
    public AcademyEntity getByAcademyName(String academyName) {
        return academyRepository.getByAcademyName(academyName);
    }

    /**
      * @Author      : QinYingran
      * @Description : 根据学校名获取学院列表
      * @Param       : [schoolName]
      * @return      : java.util.List<com.example.demo.entity.LessonEntity>
      */
    public List<AcademyEntity> getBySchoolName(String schoolName) {
        return academyRepository.getBySchoolName(schoolName);
    }

    /**
      * @Author      : QinYingran
      * @Description : 向数据库中插入学院
      * @Param       : [academyEntity]
      * @return      : void
      */
    public boolean insertAcademy(AcademyEntity academyEntity) {
        AcademyEntity academy = academyRepository.getByAcademyName(academyEntity.getAcademyName());
        if(academy!=null)
            return false;
        else {
            academyRepository.save(academyEntity);
            return true;
        }
    }

    /**
      * @Author      : QinYingran
      * @Description : 根据学院名删除学院
      * @Param       : [academyName]
      * @return      : void
      */
    public void deleteByAcademyId(String academyId){
        long id = Long.parseLong(academyId);
        academyRepository.deleteById(id);
    }

}
