package com.example.demo.repository;


import com.example.demo.entity.AcademyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
  * @Author      : QinYingran
  * @Description : 学院表接口
  */
public interface AcademyRepository extends JpaRepository<AcademyEntity,Long> {

    //根据学院名获取学院
    @Query(value = "SELECT * FROM academy WHERE academy_name = ?1",nativeQuery = true)
    AcademyEntity getByAcademyName(String academyName);

    //根据学校名获取学院列表
    @Query(value = "SELECT * FROM academy WHERE school_name = ?1",nativeQuery = true)
    List<AcademyEntity> getBySchoolName(String schoolName);
}
