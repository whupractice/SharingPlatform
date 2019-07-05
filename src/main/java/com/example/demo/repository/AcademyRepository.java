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

    //根据学院名获取学院列表
    @Query(value = "SELECT * FROM academy WHERE academy_name = ?1",nativeQuery = true)
    List<AcademyEntity> getByAcademyName(String academyName);

    //根据学校名获取学院列表
    @Query(value = "SELECT * FROM academy WHERE school_name = ?1",nativeQuery = true)
    List<AcademyEntity> getBySchoolName(String schoolName);

    //根据学校名和学院名获取学院列表
    @Query(value = "SELECT * FROM academy WHERE school_name = ?1 and academy_name = ?2",nativeQuery = true)
    List<AcademyEntity> getBySchoolNameAndAcademyName(String schoolName,String academyName);
}
