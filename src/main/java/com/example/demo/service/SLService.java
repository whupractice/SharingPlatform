package com.example.demo.service;

import com.example.demo.entity.LessonEntity;
import com.example.demo.entity.SLEntity;
import com.example.demo.entity.StudentEntity;
import com.example.demo.keys.SLKeys;
import com.example.demo.repository.LessonRepository;
import com.example.demo.repository.SLRepository;
import com.example.demo.repository.StudentRepository;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @ Author     ：Theory
 * @ Description：学生选课逻辑服务类
 */


@Service
public class SLService {

    @Autowired
    SLRepository slRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    LessonRepository lessonRepository;


    public List<SLEntity> getAll() {
        return slRepository.findAll();
    }

    /**
      * @Author      : Theory
      * @Description : 根据学生号获取学生选课列表
      * @Param       : [stuId] -- 学生账号
      * @return      : 返回该学生的学生选课信息
      */
    public List<SLEntity> getSLByStuId(String stuId){
        long newId = Long.parseLong(stuId);
        return slRepository.getSLByStuId(newId);
    }

    /**
      * @Author      : QinYingran
      * @Description : 根据课程号获取学生选课列表
      * @Param       : [lessonId]
      * @return      : 返回该课程的学生选课信息
      */
    public List<SLEntity> getSLByLessonId(String lessonId) {
        long newId = Long.parseLong(lessonId);
        return slRepository.getSLByLessonId(newId);
    }

    /**
      * @Author      : QinYingran
      * @Description : 根据学生号获取课程列表
      * @Param       : [stuId]
      * @return      : java.util.List<com.example.demo.entity.LessonEntity>
      */
    public List<LessonEntity> getLessonByStuId(String stuId) {
        long newId = Long.parseLong(stuId);
        List<LessonEntity> lessonEntities = new ArrayList<>();
        for(SLEntity slEntity : slRepository.getSLByStuId(newId)) {
            lessonEntities.add(lessonRepository.getByLessonId(slEntity.getLessonId()));
        }
        return lessonEntities;
    }

    public List<LessonEntity> getLessonPagesByStuId(String stuId,String page1,String num1){
        long id = Long.parseLong(stuId);
        int page = Integer.parseInt(page1);
        int num = Integer.parseInt(num1);
        List<LessonEntity> lessonEntities = lessonRepository.getLessonPagesByStuId(id);
        List<LessonEntity> result = new ArrayList();
        if(lessonEntities == null || lessonEntities.isEmpty()){
            return null;
        }
        int i = page * num;
        for(int j = 0; j < num; j++){
            if(lessonEntities.size()<i+j){
                return result;
            }
                result.add(lessonEntities.get(i+j));
        }
        return result;
    }

    public Object getLessonPagesNumByStuId(String stuId,String num1){
        long id = Long.parseLong(stuId);
        int num = Integer.parseInt(num1);
        List<LessonEntity> lessonEntities = lessonRepository.getLessonPagesByStuId(id);
        int result = lessonEntities.size() / num;
        String json = "{\"numOfPages\":"+"\""+result+"\"}";
        JSONObject jsonObject = JSONObject.fromObject(json);
        return jsonObject;
    }

    /**
      * @Author      : QinYingran
      * @Description : 根据课程号获取学生列表
      * @Param       : [lessonId]
      * @return      : java.util.List<com.example.demo.entity.StudentEntity>
      */
    public List<StudentEntity> getStudentByLessonId(String lessonId) {
        long newId = Long.parseLong(lessonId);
        List<StudentEntity> studentEntities = new ArrayList<>();
        for(SLEntity slEntity : slRepository.getSLByLessonId(newId)) {
            studentEntities.add(studentRepository.getStuById(slEntity.getPhone()));
        }
        return studentEntities;
    }


    public List<SLEntity> getEvaluationByLessonId(String lessonId) {
        long newId = Long.parseLong(lessonId);
        return slRepository.getEvaluationByLessonId(newId);
    }


    /**
      * @Author      : Theory
      * @Description : 查询选了这门课的学生数量
      * @Param       : [lessonId] -- 课程号
      * @return      : 这门课的选课学生数量
      */
    public Object getStuNumByLessonId(String lessonId){
        long newId = Long.parseLong(lessonId);
        int num = slRepository.getStuNumByLessonId(newId);
        String jsonData = "{\"num\":\""+num+"\"}";
        JSONObject jsonObject = JSONObject.fromObject(jsonData);
        return jsonObject;
    }



    /**
      * @Author      : Theory
      * @Description : 插入学生选课记录
      * @Param       : [sl] -- 学生选课记录
      */
    public void insertSL(SLEntity sl){

        if(sl.getEvaluation()!=null){ // 执行脚本，进行敏感词过滤
            StringBuffer res = new StringBuffer();
            try {
                File staticDir = new File(ResourceUtils.getURL("classpath:static").getPath().replace("%20"," ").replace('/', '\\'));
                File pyDir = new File(staticDir.getAbsolutePath(),"py\\");
                String py = pyDir.getAbsolutePath()+"\\filter.py";
                String arg_s = "python "+py+" "+sl.getEvaluation();
                Process proc = Runtime.getRuntime().exec(arg_s);
                BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(),"GBK"));

                String line = null;
                while ((line = in.readLine()) != null)
                {
                    res.append(line);
                }

                in.close();
                proc.waitFor();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            sl.setEvaluation(res.toString());
        }

        slRepository.save(sl);
    }



    /**
      * @Author      : Theory
      * @Description : 通过学生账号和课程账号删除选课记录
      * @Param       : [stuId,lessonId] -- 学生账号、课程账号
      */
    public void deleteSL(String stuId,String lessonId){
        long newId = Long.parseLong(stuId);
        long newId2 = Long.parseLong(lessonId);
        slRepository.deleteById(new SLKeys(newId,newId2));
    }

    /**
     * @Author      : Theory
     * @Description : 根据课程号获取课程评分
     * @Param       : [lessonId] -- 课程号
     * @return      : 课程评分
     */
    public Object getLessonScores(long lessonId){
        List<SLEntity> sls = slRepository.getSLByLessonId(lessonId);
        float sum = 0;
        int num = 0;
        for(SLEntity sl : sls){
            if(sl.getStar()!=0){
                sum+=sl.getStar();
                num++;
            }
        }
        float score = sum/num;
        String json = "{\"score\":"+score+"}";
        JSONObject jsonObject = JSONObject.fromObject(json);
        return jsonObject;
    }
    
}
