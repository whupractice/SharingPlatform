package com.example.demo.service;

import org.springframework.stereotype.Service;

/**
 * @ Author     ：Theory
 * @ Description：初始化数据库的逻辑设计类
 */


@Service
public class InitService {
//
//    @Autowired
//    TeacherRepository teacherRepository;
//
//    @Autowired
//    LessonRepository lessonRepository;
//
//    @Autowired
//    TLRepository tlRepository;
//
//    @Autowired
//    SchoolRepository schoolRepository;
//
//    private final Logger logger = LoggerFactory.getLogger(InitService.class);
//
//    public void insertT_L_TL(){
//            ClassCrawler classCrawler = new ClassCrawler();
//            List<Lesson> lessons = classCrawler.crawl1();
//
//            //遍历课程链表
//            for (Lesson lesson : lessons) {
//                /*插入学校*/
//                System.out.println(lesson);
//                try {
//                    String schoolName = lesson.getTeachers().get(0).getSchoolName();
//                    List<SchoolEntity> schoolDuplicates = schoolRepository.getDuplicates(schoolName);
//                    if(schoolDuplicates.isEmpty()) {
//                        SchoolEntity schoolEntity = new SchoolEntity(schoolName);//获得学校
//                        schoolRepository.save(schoolEntity);
//                    }
//                    /*插入课程*/
//                    String lessonName = lesson.getLessonName();
//                    List<LessonEntity> lessonDuplicates = lessonRepository.getDuplicates(lessonName,schoolName);
//                    long lessonId;
//                    if(lessonDuplicates.isEmpty()) {
//                        LessonEntity lessonEntity = new LessonEntity(lesson.getLessonName(),
//                                lesson.getSchoolName(), lesson.getSubject(), lesson.getCategory(), lesson.getCredit(),
//                                lesson.getStartTime(), lesson.getEndTime(), lesson.getStatus(), lesson.getIntro(), 0, 0, 0,
//                                lesson.getImgLink());
//                        lessonRepository.save(lessonEntity);
//                        lessonId = lessonRepository.getNewLessonId();//新注册的课程号
//                    } else {
//                        lessonId = lessonDuplicates.get(0).getLessonId();
//                    }
//
//                    /*插入老师以及完善 课程-教师 表*/
//                    for (Teacher teacher : lesson.getTeachers()) {
//                        List<TeacherEntity> teacherDuplicates = teacherRepository.getDuplicates(teacher.getTeacherName(),teacher.getIntroduction());
//                        long teacherId;
//                        if(teacherDuplicates.isEmpty()) {
//                            TeacherEntity teacherEntity = new TeacherEntity(teacher.getTeacherName(),
//                                    teacher.getJob(), teacher.getSchoolName(), teacher.getAcademyName(), teacher.getIntroduction(),
//                                    teacher.getImgLink());
//                            teacherRepository.save(teacherEntity);
//                            teacherId = teacherRepository.getNewTeacherId();//新注册的教师号
//                        } else {
//                            teacherId = teacherDuplicates.get(0).getTeacherId();
//                        }
//                        TLEntity tl = new TLEntity(lessonId, teacherId);
//                        tlRepository.save(tl);//插入课程-教师
//                    }
//                } catch (Exception e) {
//
//                }
//
//            }
//    }
//
//    //插入学校信息
//    public void insertSchoolInfo(){
//        ClassCrawler classCrawler = new ClassCrawler();
//        List<School> schools = classCrawler.crawl2();
//        for(School school:schools){
//            logger.info(school.toString());
//        }
//    }

}
