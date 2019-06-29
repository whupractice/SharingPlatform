package com.example.demo.repository;


import com.example.demo.entity.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author      : QinYingran
 * @Description : 学校表接口
 */
public interface SchoolRepository extends JpaRepository<SchoolEntity,Long> {

    //查询特定学校号的学校简介
    @Query(value = "SELECT school_itro FROM school WHERE school_id = ?1",nativeQuery = true)
    String getSchoolInfoById(Long id);

    //查询全部学校
    @Query(value = "select distinct school_itro,school_name from school order by school_name",nativeQuery = true)
    List<Object> getAllDistinctly();

    //查询含有关键词的学校并去重
    @Query(value = "select distinct school_itro,school_name from school where school_name like ?1 order by school_name",nativeQuery = true)
    List<Object> getSchoolByKeyword(String keyword);

}

