package com.example.demo.service;

import com.example.demo.baseClass.Lesson;
import com.example.demo.baseClass.Teacher;
import com.example.demo.crawler.ClassCrawler;
import com.example.demo.entity.LessonEntity;
import com.example.demo.entity.SchoolEntity;
import com.example.demo.entity.TLEntity;
import com.example.demo.entity.TeacherEntity;
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
                String schoolName = lesson.getTeachers().get(0).getSchoolName();
                List<SchoolEntity> schoolDuplicates = schoolRepository.getDuplicates(schoolName);
                if(schoolDuplicates.isEmpty()) {
                    SchoolEntity schoolEntity = new SchoolEntity(schoolName);//获得学校
                    schoolRepository.save(schoolEntity);
                }

                /*插入课程*/
                String lessonName = lesson.getLessonName();
                List<LessonEntity> lessonDuplicates = lessonRepository.getDuplicates(lessonName,schoolName);
                long lessonId;
                if(lessonDuplicates.isEmpty()) {
                    LessonEntity lessonEntity = new LessonEntity(lesson.getLessonName(),
                            lesson.getSchoolName(), lesson.getSubject(), lesson.getCategory(), lesson.getCredit(),
                            lesson.getStartTime(), lesson.getEndTime(), lesson.getStatus(), lesson.getIntro(), 0, false, 0,
                            lesson.getImgLink());
                    lessonRepository.save(lessonEntity);
                    lessonId = lessonRepository.getNewLessonId();//新注册的课程号
                } else {
                    lessonId = lessonDuplicates.get(0).getLessonId();
                }

                /*插入老师以及完善 课程-教师 表*/
                for (Teacher teacher : lesson.getTeachers()) {
                    List<TeacherEntity> teacherDuplicates = teacherRepository.getDuplicates(teacher.getTeacherName(),teacher.getIntroduction());
                    long teacherId;
                    if(teacherDuplicates.isEmpty()) {
                        TeacherEntity teacherEntity = new TeacherEntity(teacher.getTeacherName(),
                                teacher.getJob(), teacher.getSchoolName(), teacher.getAcademyName(), teacher.getIntroduction(),
                                teacher.getImgLink());
                        teacherRepository.save(teacherEntity);
                        teacherId = teacherRepository.getNewTeacherId();//新注册的教师号
                    } else {
                        teacherId = teacherDuplicates.get(0).getTeacherId();
                    }
                    TLEntity tl = new TLEntity(lessonId, teacherId);
                    tlRepository.save(tl);//插入课程-教师
                }
            }
    }

}
