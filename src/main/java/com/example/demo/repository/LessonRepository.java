package com.example.demo.repository;

import com.example.demo.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
  * @Author      : Theory
  * @Description : 课程表接口
  */
public interface LessonRepository extends JpaRepository<LessonEntity,Long>, JpaSpecificationExecutor<LessonEntity> {

    //查询精品课程
    @Query(value = "SELECT * FROM lesson WHERE is_excellent = 1",nativeQuery = true)
    List<LessonEntity> getExcellentClass();

    //返回最大的课程id（最新加入的课程id）
    @Query(value = "SELECT MAX(lesson_id) FROM lesson",nativeQuery = true)
    long getNewLessonId();

    //查询全部课程
    @Query(value = "select distinct credit,education,end_time,img_link,is_excellent,lesson_intro,lesson_name,school_name,share_num,start_time,status,subject,welcome from lesson order by subject",nativeQuery = true)
    List<Object> getAllDistinctly();

    //查询含有关键词的课程
    //@Query(value = "select distinct credit,education,end_time,img_link,is_excellent,lesson_intro,lesson_name,school_name,share_num,start_time,status,subject,welcome from lesson where lesson_name like ?1 order by subject",nativeQuery = true)
    @Query(value = "select * from lesson where lesson_name like ?1 order by subject",nativeQuery = true)
    List<Object> getLessonByKeyword(String keyword);

    //查询重复项（课程名和学校名）
    @Query(value = "select * from lesson where lesson_id in (select lesson_id from lesson where lesson_name = ?1 and school_name = ?2)",nativeQuery = true)
    List<LessonEntity> getDuplicates(String lessonName,String schoolName);

    //根据状态查询课程
    @Query(value = "select * from lesson where status = ?1",nativeQuery = true)
    List<LessonEntity> getByStatus(String status);

    //根据学科查询课程
    @Query(value = "select * from lesson where subject = ?1",nativeQuery = true)
    List<LessonEntity> getBySubject(String subject);

    //根据学校名查询课程
    @Query(value = "select * from lesson where school_name = ?1",nativeQuery = true)
    List<LessonEntity> getBySchoolName(String schoolName);

    //根据课程id查询课程
    @Query(value = "select * from lesson where lesson_id = ?1",nativeQuery = true)
    LessonEntity getByLessonId(long lessonId);

    //根据状态和学科查询课程
    @Query(value = "select * from lesson where status = ?1 and subject = ?2",nativeQuery = true)
    List<LessonEntity> getByStatusAndSubject(String status,String subject);

}
