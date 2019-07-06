package com.example.demo.service;

import com.example.demo.entity.LessonEntity;
import com.example.demo.entity.MessageEntity;
import com.example.demo.entity.StudentEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @ Author     ：QinYingran
 * @ Description：
 */
@Service
public class SpecUtil {

    public Specification<MessageEntity> createSpecificationByPhone(String phone) {
        return (Specification<MessageEntity>) (root, query, cb) -> {
            //用于暂时存放查询条件的集合
            List<Predicate> predicatesList = new ArrayList<>();

            Predicate predicate = cb.like(root.get("phone"), "%" + phone + "%");
            predicatesList.add(predicate);

            Predicate[] predicates = new Predicate[predicatesList.size()];
            return cb.and(predicatesList.toArray(predicates));
        };
    }

    public Specification<MessageEntity> createSpecificationByPhoneAndLessonId(String phone, String lessonId) {
        return (Specification<MessageEntity>) (root, query, cb) -> {
            //用于暂时存放查询条件的集合
            List<Predicate> predicatesList = new ArrayList<>();

            Predicate predicate = cb.like(root.get("phone"), "%" + phone + "%");
            predicatesList.add(predicate);

            Predicate predicate1 = cb.like(root.get("lessonId"), "%" + lessonId + "%");
            predicatesList.add(predicate1);

            Predicate[] predicates = new Predicate[predicatesList.size()];
            return cb.and(predicatesList.toArray(predicates));
        };
    }


    public Specification<LessonEntity> createSpecificationByStatusAndSubject(String status, String subject) {

        return (Specification<LessonEntity>) (root, query, cb) -> {
            //用于暂时存放查询条件的集合
            List<Predicate> predicatesList = new ArrayList<>();

            if (!status.equals("全部")) {
                Predicate predicate = cb.like(root.get("status"), "%" + status + "%");
                predicatesList.add(predicate);
            }
            if (!subject.equals("全部")) {
                Predicate predicate = cb.like(root.get("subject"), "%" + subject + "%");
                predicatesList.add(predicate);
            }
            Predicate[] predicates = new Predicate[predicatesList.size()];
            return cb.and(predicatesList.toArray(predicates));
        };

    }

    public Specification<LessonEntity> createSpecificationBySchoolName(String schoolName) {

        return (Specification<LessonEntity>) (root, query, cb) -> {
            //用于暂时存放查询条件的集合
            List<Predicate> predicatesList = new ArrayList<>();


            Predicate predicate = cb.like(root.get("schoolName"), "%" + schoolName + "%");
            predicatesList.add(predicate);

            Predicate[] predicates = new Predicate[predicatesList.size()];
            return cb.and(predicatesList.toArray(predicates));
        };

    }

    public Specification<LessonEntity> createSpecificationByLessonName(String lessonName) {

        return (Specification<LessonEntity>) (root, query, cb) -> {
            //用于暂时存放查询条件的集合
            List<Predicate> predicatesList = new ArrayList<>();


            Predicate predicate = cb.like(root.get("lessonName"), "%" + lessonName + "%");
            predicatesList.add(predicate);

            Predicate[] predicates = new Predicate[predicatesList.size()];
            return cb.and(predicatesList.toArray(predicates));
        };

    }

    public Specification<LessonEntity> createSpecificationBySchoolAndAcademy(String schoolName,String academyName) {

        return (Specification<LessonEntity>) (root, query, cb) -> {
            //用于暂时存放查询条件的集合
            List<Predicate> predicatesList = new ArrayList<>();


            Predicate predicate = cb.like(root.get("schoolName"), "%" + schoolName + "%");
            predicatesList.add(predicate);

            Predicate predicate2 = cb.like(root.get("academyName"), "%" + academyName + "%");
            predicatesList.add(predicate2);

            Predicate[] predicates = new Predicate[predicatesList.size()];
            return cb.and(predicatesList.toArray(predicates));
        };

    }

    public Specification<StudentEntity> createLessonManagerSpecification() {

        return (Specification<StudentEntity>) (root, query, cb) -> {
            //用于暂时存放查询条件的集合
            List<Predicate> predicatesList = new ArrayList<>();

            Predicate predicate = cb.equal(root.get("isLessonManager"),1);
            predicatesList.add(predicate);

            Predicate[] predicates = new Predicate[predicatesList.size()];
            return cb.and(predicatesList.toArray(predicates));
        };
    }

    public Specification<StudentEntity> createLessonManagerSpecificationByName(String realName) {

        return (Specification<StudentEntity>) (root, query, cb) -> {
            //用于暂时存放查询条件的集合
            List<Predicate> predicatesList = new ArrayList<>();


            Predicate predicate = cb.like(root.get("realName"), "%" + realName + "%");
            predicatesList.add(predicate);

            Predicate predicate1 = cb.equal(root.get("isLessonManager"),1);
            predicatesList.add(predicate1);

            Predicate[] predicates = new Predicate[predicatesList.size()];
            return cb.and(predicatesList.toArray(predicates));
        };
    }


    public Specification<StudentEntity> createLessonManagerSpecificationBySchool(String schoolName) {

        return (Specification<StudentEntity>) (root, query, cb) -> {
            //用于暂时存放查询条件的集合
            List<Predicate> predicatesList = new ArrayList<>();

            Predicate predicate = cb.like(root.get("schoolName"), "%" + schoolName + "%");
            predicatesList.add(predicate);

            Predicate predicate1 = cb.equal(root.get("isLessonManager"),1);
            predicatesList.add(predicate1);

            Predicate[] predicates = new Predicate[predicatesList.size()];
            return cb.and(predicatesList.toArray(predicates));
        };
    }
}
