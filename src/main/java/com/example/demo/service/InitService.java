package com.example.demo.service;

import com.example.demo.baseClass.Lesson;
import com.example.demo.baseClass.School;
import com.example.demo.baseClass.Teacher;
import com.example.demo.crawler.ClassCrawler;
import com.example.demo.entity.*;
import com.example.demo.repository.LessonRepository;
import com.example.demo.repository.SchoolRepository;
import com.example.demo.repository.TLRepository;
import com.example.demo.repository.TeacherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author     ：Theory
 * @ Description：初始化数据库的逻辑设计类
 */


@Service
public class InitService {

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    TLRepository tlRepository;

    @Autowired
    SchoolRepository schoolRepository;

    private final Logger logger = LoggerFactory.getLogger(InitService.class);

    public void insertT_L_TL(){
            ClassCrawler classCrawler = new ClassCrawler();
            List<Lesson> lessons = classCrawler.crawl();

            //遍历课程链表
            for (Lesson lesson : lessons) {
                /*插入学校*/
                SchoolEntity school = new SchoolEntity(lesson.getTeachers().get(0).getSchoolName());//获得学校
                schoolRepository.save(school);

                /*插入课程*/
                LessonEntity lessonEntity = new LessonEntity(lesson.getLessonName(),
                        lesson.getSchoolName(), lesson.getSubject(), lesson.getCategory(), lesson.getCredit(),
                        lesson.getStartTime(), lesson.getEndTime(), lesson.getStatus(), lesson.getIntro(), 0, false, 0,
                        lesson.getImgLink());
                lessonRepository.save(lessonEntity);
                long lessonId = lessonRepository.getNewLessonId();//新注册的课程号

                /*插入老师以及完善 课程-教师 表*/
                for (Teacher teacher : lesson.getTeachers()) {
                    TeacherEntity teacherEntity = new TeacherEntity(teacher.getTeacherName(),
                            teacher.getJob(), teacher.getSchoolName(), teacher.getAcademyName(), teacher.getIntroduction(),
                            teacher.getImgLink());
                    teacherRepository.save(teacherEntity);
                    long teacherId = teacherRepository.getNewTeacherId();//新注册的教师号
                    TLEntity tl = new TLEntity(lessonId, teacherId);
                    tlRepository.save(tl);//插入课程-教师
                }
            }
    }

    public void insertTeacher(){

    }
}
