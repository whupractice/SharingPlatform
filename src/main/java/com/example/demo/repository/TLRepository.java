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

    //查询特定课程的教师信息
    @Query(value = "SELECT * FROM TL WHERE lesson_id = ?1",nativeQuery = true)
    List<TLEntity> getAllByLessonId(long id);

}
