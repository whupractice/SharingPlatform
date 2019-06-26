package com.example.demo.repository;


import com.example.demo.entity.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author      : QinYingran
 * @Description : 学校表接口
 */
public interface SchoolRepository extends JpaRepository<SchoolEntity,Long> {

    //查询特定学校号的学校简介
    @Query(value = "SELECT school_itro FROM school WHERE school_id = ?1",nativeQuery = true)
    String getSchoolInfoById(Long id);

}

