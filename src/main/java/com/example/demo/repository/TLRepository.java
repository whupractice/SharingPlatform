package com.example.demo.repository;

import com.example.demo.entity.TLEntity;
import com.example.demo.keys.TLKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
  * @Author      : QinYingran
  * @Description : 教师授课表接口
  * @Param       :
  * @return      :
  */
public interface TLRepository extends JpaRepository<TLEntity, TLKeys> {

    //根据课程id查询教师授课信息
    @Query(value = "SELECT * FROM TL WHERE lesson_id = ?1",nativeQuery = true)
    List<TLEntity> getAllByLessonId(long id);

    //查询重复项
    @Query(value = "select * from tl where lesson_id = ?1 and teacher_id = ?2",nativeQuery = true)
    List<TLEntity> getDuplicates(long lessonId,long teacherId);

    //根据教师id查询教师授课信息
    @Query(value = "SELECT * FROM TL WHERE teacher_id = ?1",nativeQuery = true)
    List<TLEntity> getAllByTeacherId(long teacherId);

}
