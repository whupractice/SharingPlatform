package com.example.demo.controller;

import com.example.demo.entity.LessonEntity;
import com.example.demo.service.LessonService;
import com.example.demo.service.SpecUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;


/**
 * @ Author     ：Theory
 * @ Description：课程控制类
 */

@Api(value = "LessonController|课程控制器")
@RestController
@RequestMapping("/lesson")
public class LessonController {


    @Autowired
    LessonService lessonService;

    @Autowired
    SpecUtil specUtil;





//    @ApiOperation(value = "获取项目根目录", notes = "获取项目根目录",httpMethod = "GET")
//    @GetMapping("/getRootUrl")
//    public JSONObject getRootUrl(){
//        String LOCATION;
//        try {
//            LOCATION = URLDecoder.decode(DemoApplication.class.getProtectionDomain().getCodeSource().getLocation().getFile(),
//                    "UTF-8");
//            System.out.println("获取路径成功：LOCATION=" + LOCATION);
//            LOCATION = LOCATION.substring(0,LOCATION.length()-39);
//        } catch (UnsupportedEncodingException e) {
//            System.out.println("获取路径失败：" + e.getMessage());
//            LOCATION = "";
//        }
//        String content ="{\"link\":\""+LOCATION+"\"}";
//        JSONObject jsonObject = JSONObject.fromObject(content);
//        getPath("video");
//        return jsonObject;
//
//
//    }

//    private String getPath(String subdirectory){
//        //获取跟目录---与jar包同级目录的upload目录下指定的子目录subdirectory
//        File upload;
//        try {
//            File path = new File(ResourceUtils.getURL("classpath:").getPath());
//            if (!path.exists()) path = new File("");
//            upload = new File(path.getAbsolutePath(),subdirectory);
//            if(!upload.exists())upload.mkdirs();
//            String realPath = upload + "/";
//            return realPath;
//        }catch (FileNotFoundException e){
//            throw new RuntimeException("获取服务器路径发生错误！");
//        }
//    }


    /**
     * @Author      : Theory
     * @Description : 获取所有课程
     * @return      : 所有课程list
     */
    @PreAuthorize("hasRole('manager')")
    @ApiOperation(value = "获取所有课程", notes = "获取所有课程",httpMethod = "GET")
    @GetMapping("")
    public List<LessonEntity> getAllLesson(){
        return lessonService.getAll();
    }


    @ApiOperation(value = "根据课程id获取课程", notes = "根据课程id获取课程",httpMethod = "GET")
    @ApiParam(name = "lessonId",value = "课程id")
    @GetMapping("/id")
    public LessonEntity getByLessonId(@RequestParam String lessonId) {
        return lessonService.getByLessonId(lessonId);
    }


    /**
     * @Author      : Theory
     * @Description : 获取精品课程
     * @return      : 精品课程list
     */
    @ApiOperation(value = "获取精品课程", notes = "获取精品课程",httpMethod = "GET")
    @GetMapping("/excellent")
    public List<LessonEntity> getExcellentLesson(){
        return lessonService.getExcellentLesson();
    }

    /**
     * @Author      : Theory
     * @Description : 获取热门课程
     * @Param       :  [lessonNum] -- 需要的热门课程数量
     * @return      : 热门课程list
     */
    @ApiOperation(value = "获取n个热门课程", notes = "获取n个热门课程",httpMethod = "GET")
    @ApiParam(name = "lessonNum",value = "所要获取热门课程数")
    @GetMapping("/hot")
    public List<LessonEntity> getHotLesson(@RequestParam("lessonNum") int lessonNum){
        return lessonService.getHotLesson(lessonNum);
    }

    @ApiOperation(value = "根据学校名获取课程列表", notes = "根据学校名获取课程列表",httpMethod = "GET")
    @GetMapping("/schoolName")
    @ApiParam(name = "schoolName",value = "学校名")
    public List<LessonEntity> getBySchoolName(@RequestParam String schoolName) {
        return lessonService.getBySchoolName(schoolName);
    }

//    @ApiOperation(value = "根据课程名模糊获取课程列表", notes = "根据课程名模糊获取课程列表",httpMethod = "GET")
//    @ApiParam(name = "keyword",value = "课程名关键词")
//    @GetMapping("/keyword")
//    public List<Object> getLessonByKeyword(@RequestParam String keyword) {
//
//        return lessonService.getLessonByKeyword(keyword);
//    }


    @ApiOperation(value = "分页根据课程状态和学科获取课程列表", notes = "分页根据课程状态和学科获取课程列表",httpMethod = "GET")
    @GetMapping("/pages")
    public Page<LessonEntity> getLessonPages(@PageableDefault(size = 12, sort = {"welcome"}, direction = Sort.Direction.DESC)@ApiParam(value = "分页信息") Pageable pageable,
                                             @RequestParam(value = "status")@ApiParam(value = "课程状态") String status,
                                             @RequestParam(value = "subject")@ApiParam(value = "学科") String subject) {
        Specification<LessonEntity> specification = specUtil.createSpecificationByStatusAndSubject(status,subject);
        return lessonService.getAll(specification,pageable);
    }


    @ApiOperation(value = "分页根据学校名关键字获取课程列表", notes = "分页根据学校名关键字获取课程列表",httpMethod = "GET")
    @GetMapping("/pagesBySchoolName")
    public Page<LessonEntity> getLessonPagesBySchoolName(@PageableDefault(size = 12, sort = {"welcome"}, direction = Sort.Direction.DESC)@ApiParam(value = "分页信息") Pageable pageable,
                                                         @RequestParam(value = "schoolName")@ApiParam(value = "学校名") String schoolName) {
        Specification<LessonEntity> specification = specUtil.createSpecificationBySchoolName(schoolName);
        return lessonService.getAll(specification,pageable);
    }

    @ApiOperation(value = "分页根据课程名关键字获取课程列表", notes = "分页根据课程名关键字获取课程列表",httpMethod = "GET")
    @GetMapping("/pagesByLessonName")
    public Page<LessonEntity> getLessonPagesByLessonName(@PageableDefault(size = 12, sort = {"welcome"}, direction = Sort.Direction.DESC)@ApiParam(value = "分页信息") Pageable pageable,
                                                         @RequestParam(value = "lessonName")@ApiParam(value = "课程名") String lessonName) {
        Specification<LessonEntity> specification = specUtil.createSpecificationByLessonName(lessonName);
        return lessonService.getAll(specification,pageable);
    }


    /**
     * @Author      : Theory
     * @Description : 向数据库中插入课程
     * @Param       : [lesson] -- 课程
     */
    @PreAuthorize("hasRole('lessonManager')")
    @ApiOperation(value = "向数据库中插入课程", notes = "向数据库中插入课程",httpMethod = "POST")
    @ApiParam(name = "lesson",value = "课程实体，其中lessonId、credit、isExcellent、shareNum、welcome不能为空")
    @PostMapping("")
    public void insertLesson(@RequestBody LessonEntity lesson){
        lessonService.insertLesson(lesson);
    }


    /**
     * @Author      : Theory
     * @Description : 更新课程
     * @Param       : [lesson] -- 课程
     */
    @ApiOperation(value = "更新课程", notes = "更新课程",httpMethod = "PUT")
    @ApiParam(name = "lesson",value = "课程实体，其中lessonId、credit、isExcellent、shareNum、welcome不能为空")
    @PutMapping("")
    public void updateLesson(@RequestBody LessonEntity lesson){
        lessonService.insertLesson(lesson);
    }



    /**
     * @Author      : Theory
     * @Description : 根据课程id删除课程
     * @Param       : [lessonId] -- 课程id
     */
    @PreAuthorize("hasRole('lessonManager')")
    @ApiOperation(value = "根据课程id删除课程", notes = "根据课程id删除课程",httpMethod = "DELETE")
    @ApiParam(name = "lessonId",value = "课程号")
    @DeleteMapping("")
    public void deleteLesson(@RequestParam("lessonId") long lessonId){
        lessonService.deleteLesson(lessonId);
    }


    @PreAuthorize("hasRole('lessonManager')")
    @ApiOperation(value = "根据学校和学院获取课程",notes = "根据学校和学院获取课程",httpMethod = "GET")
    @GetMapping("/schoolAndAcademy")
    public List<LessonEntity> getLessonsBySchoolAndAcademy(@RequestParam(value = "schoolName")@ApiParam(name="schoolName",value = "学校名") String schoolName,
                                                           @RequestParam(value = "academyName")@ApiParam(name="academyName",value = "学院名") String academyName){
        return lessonService.getLessonsBySchoolAndAcademy(schoolName,academyName);
    }

    @ApiOperation(value = "根据学校和学院分页获取课程列表", notes = "根据学校和学院分页获取课程列表",httpMethod = "GET")
    @GetMapping("/pagesBySchoolAndAcademy")
    public Page<LessonEntity> getLessonPagesBySchoolAndAcademy(@PageableDefault(size = 12, sort = {"welcome"}, direction = Sort.Direction.DESC)@ApiParam(value = "分页信息") Pageable pageable,
                                                               @RequestParam(value = "schoolName")@ApiParam(value = "学校名") String schoolName,
                                                               @RequestParam(value = "academyName")@ApiParam(value = "学院名") String academyName) {
        Specification<LessonEntity> specification = specUtil.createSpecificationBySchoolAndAcademy(schoolName,academyName);
        return lessonService.getAll(specification,pageable);
    }


    @PreAuthorize("hasRole('student')")
    @ApiOperation(value = "根据学生获取推荐课程",notes = "根据学生获取推荐课程",httpMethod = "GET")
    @GetMapping("/tj")
    public List<LessonEntity> getTjLessonByStuPhone(@RequestParam(value = "phone")
                                                        @ApiParam(value = "phone") long phone){
        return lessonService.getTjLessonByStuPhone(phone);
    }




    @ApiOperation(value = "上传课程图片",notes = "上传课程图片")
    @PostMapping("/imgUpload")
    public void uploadImg(@RequestParam("img")@ApiParam(value = "img") MultipartFile file,
                       @RequestParam("fileName")@ApiParam(value = "fileName") String fileName){
        try {
            String path3 = Thread.currentThread().getContextClassLoader().getResource("").getPath()+"template";
            File path2 = new File(ResourceUtils.getURL("classpath:static").getPath().replace("%20"," ").replace('/', '\\'));
            if(!path2.exists()) path2 = new File("");
            //如果上传目录为/static/img/lesson/，则可以如下获取：
            File upload2 = new File(path2.getAbsolutePath(),"img/lesson/");
            if(!upload2.exists()) upload2.mkdirs();
            String path=upload2.getAbsolutePath()+"/"+fileName;
            File img = new File(path);
            if(!img.exists())
                img.createNewFile();//不存在则创建新文件
            file.transferTo(img);
            LessonEntity oldLesson = lessonService.getByLessonId(fileName);
            oldLesson.setImgLink("../img/lesson/"+fileName);
            lessonService.insertLesson(oldLesson);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "上传课程视频",notes = "上传课程视频")
    @PostMapping("/videoUpload")
    public void uploadVideo(@RequestParam("video")@ApiParam(value = "video") MultipartFile file,
                       @RequestParam("id")@ApiParam(value = "id") String id,
                       @RequestParam("fileName")@ApiParam(value = "fileName") String fileName){
        try {
            File path2 = new File(ResourceUtils.getURL("classpath:static").getPath().replace("%20"," ").replace('/', '\\'));
            if(!path2.exists()) path2 = new File("");
            //如果上传目录为/static/img/lesson/，则可以如下获取：
            File upload2 = new File(path2.getAbsolutePath(),"video");
            if(!upload2.exists()) upload2.mkdirs();
            String path=upload2.getAbsolutePath()+"/"+fileName;
            File img = new File(path);
            if(!img.exists())
                img.createNewFile();//不存在则创建新文件
            file.transferTo(img);
            LessonEntity oldLesson = lessonService.getByLessonId(fileName);
            oldLesson.setVideoLink("../video/"+fileName);
            lessonService.insertLesson(oldLesson);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
