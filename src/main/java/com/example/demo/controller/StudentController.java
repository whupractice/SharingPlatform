package com.example.demo.controller;

import com.example.demo.entity.StudentEntity;
import com.example.demo.service.SpecUtil;
import com.example.demo.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * @ Author     ：Theory
 * @ Description：学生控制类
 */

@Api(value = "StudentController|学生控制器")
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @Autowired
    SpecUtil specUtil;

    @ApiOperation(value = "根据token获取手机号和密码", notes = "根据token获取手机号和密码",httpMethod = "GET")
    @GetMapping(value = "/getUserFromToken")
    public Object getUserFromToken(@RequestParam @ApiParam(value = "token") String token) {
        UserDetails userDetails = studentService.getUserFromToken(token);
        if(userDetails==null){
            return null;
        }
        String phone = userDetails.getUsername();
        String pwd = userDetails.getPassword();
        String json = "{\"phone\":"+"\""+phone+"\",\"pwd\":"+"\""+pwd+"\"}";
        JSONObject jsonObject = JSONObject.fromObject(json);
        return jsonObject;
    }

    /**
      * @Author      : Theory
      * @Description : 登陆验证
      * @Param       : [user, pwd] -- 学生账号，密码
      * @return      : 学生实体
      */
    @ApiOperation(value = "登陆验证", notes = "登陆验证",httpMethod = "POST")
    @PostMapping(value = "/login")
    public Object login(@RequestBody StudentEntity student) {
        String token = studentService.judgeLogin(student.getPhone(),student.getPwd());
        if(token==null){
            return null;
        }
        String json = "{\"token\":"+"\""+token+"\"}";
        JSONObject jsonObject = JSONObject.fromObject(json);
        return jsonObject;
    }

    @ApiOperation(value = "退出登录", notes = "退出登录",httpMethod = "DELETE")
    @DeleteMapping(value = "/logout")
    public boolean logout(HttpServletRequest request){
        String token;
        String requestHeader = request.getHeader("Authorization");
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            token = requestHeader.substring(7);
            studentService.logout(token);
            return true;
        } else {
            return false;
        }
    }


    //不加限制
    @ApiOperation(value = "根据学生手机号获取昵称", notes = "根据学生手机号获取昵称",httpMethod = "GET")
    @GetMapping(value = "/getNickName")
    public StudentEntity getNickNameByPhone(@RequestParam @ApiParam(value = "学生手机号") String phone) {
        return studentService.getNickNameByPhone(phone);
    }



    /**
      * @Author      : Theory
      * @Description : 注册学生账号
      * @Param       : [pwd] -- 学生密码
      * @return      : 学生账号
      */
    @ApiOperation(value = "注册学生账号", notes = "注册学生账号",httpMethod = "POST")
    @PostMapping(value = "/register")
    @ApiParam(name = "stu",value = "学生实体,其中phone和pwd不能为空")
    public Object register(@RequestBody StudentEntity stu){
        if(stu.getIsLessonManager()==0&&stu.getIsManager()==0){
            return doRegister(stu);
        }
        return null;
    }

    @ApiOperation(value = "注册课程管理员账号", notes = "注册课程管理员账号",httpMethod = "POST")
    @PostMapping(value = "/registerLessonManager")
    @ApiParam(name = "stu",value = "课程管理员实体,其中phone和pwd不能为空")
    public Object registerLessonManager(@RequestBody StudentEntity stu){
        if(stu.getIsLessonManager()==1&&stu.getIsManager()==0){
            return doRegister(stu);
        }
        return null;
    }

    @ApiOperation(value = "注册系统管理员账号", notes = "注册系统管理员账号",httpMethod = "POST")
    @PostMapping(value = "/registerManager")
    @ApiParam(name = "stu",value = "系统管理员实体,其中phone和pwd不能为空")
    public Object registerManager(@RequestBody StudentEntity stu){
        if(stu.getIsLessonManager()==0&&stu.getIsManager()==1){
            return doRegister(stu);
        }
        return null;
    }

    private Object doRegister(StudentEntity stu){
        String token = studentService.register(stu);
        String json = "{\"token\":"+"\""+token+"\"}";
        JSONObject jsonObject = JSONObject.fromObject(json);
        return jsonObject;
    }


    @PreAuthorize("hasRole('student')")
    @ApiOperation(value = "根据id获取学生信息", notes = "根据id获取学生信息",httpMethod = "GET")
    @GetMapping(value = "/info")
    public StudentEntity getStuById(@RequestParam(value = "phone") @ApiParam(value = "学生账号") String phone){
        return studentService.getStuById(phone);
    }

    @PreAuthorize("hasRole('student')")
    @ApiOperation(value = "根据昵称获取学生", notes = "根据昵称获取学生",httpMethod = "GET")
    @GetMapping(value = "/nickName")
    @ApiParam(name = "nickName",value = "昵称")
    public StudentEntity getStuByNickName(@RequestParam String nickName) {
        return studentService.getStuByNickName(nickName);
    }


    @ApiOperation(value = "根据学校名获取学生", notes = "根据学校名获取学生",httpMethod = "GET")
    @GetMapping(value = "/schoolName")
    @ApiParam(name = "schoolName",value = "学校名")
    public List<StudentEntity> getStuBySchoolName(@RequestParam String schoolName) {
        return studentService.getStuBySchoolName(schoolName);
    }


    /**
      * @Author      : Theory
      * @Description : 返回数据库中所有学生的信息
      * @return      : 所有学生信息
      */
    @ApiOperation(value = "获取数据库中所有学生的信息", notes = "获取数据库中所有学生的信息",httpMethod = "GET")
    @GetMapping(value = "")
    public List<StudentEntity> getAllStudent(){
        return studentService.getAllStudent();
    }


    @PreAuthorize("hasRole('manager')")
    @ApiOperation(value = "获取数据库中所有系统管理员的信息", notes = "获取数据库中所有系统管理员的信息",httpMethod = "GET")
    @GetMapping(value = "/manager")
    public List<StudentEntity> getAllManager() {
        return studentService.getAllManager();
    }


    /**
     * @Author      : Theory
     * @Description : 更新指定学生记录
     * @Param       : [stu] -- 学生实体
     */
    @PreAuthorize("hasRole('student')")
    @ApiOperation(value = "更新指定学生记录", notes = "不允许改phone！！！",httpMethod = "PUT")
    @ApiParam(name = "stu",value = "学生实体,其中phone和pwd不能为空")
    @PutMapping("/updateStudent")
    public Object updateStudent(@RequestBody StudentEntity stu){
        if(stu.getIsLessonManager()==0&&stu.getIsManager()==0){
            return doUpdate(stu);
        }
        return null;
    }

    @PreAuthorize("hasRole('lessonManager')")
    @ApiOperation(value = "更新指定课程管理员记录", notes = "不允许改phone！！！",httpMethod = "PUT")
    @ApiParam(name = "stu",value = "课程管理员实体,其中phone和pwd不能为空")
    @PutMapping("/updateLessonManager")
    public Object updateLessonManager(@RequestBody StudentEntity stu){
        if(stu.getIsLessonManager()==1&&stu.getIsManager()==0){
            return doUpdate(stu);
        }
        return null;
    }

    @PreAuthorize("hasRole('manager')")
    @ApiOperation(value = "更新指定系统管理员", notes = "不允许改phone！！！",httpMethod = "PUT")
    @ApiParam(name = "stu",value = "系统管理员实体,其中phone和pwd不能为空")
    @PutMapping("/updateManager")
    public Object updateManager(@RequestBody StudentEntity stu){
        if(stu.getIsLessonManager()==0&&stu.getIsManager()==1){
            return doUpdate(stu);
        }
        return null;
    }

    private Object doUpdate(StudentEntity student){
        if(studentService.insertStudent(student)){
            String token = studentService.judgeLogin(student.getPhone(),student.getPwd());
            String json = "{\"token\":"+"\""+token+"\"}";
            JSONObject jsonObject = JSONObject.fromObject(json);
            return jsonObject;
        }
        return null;
    }


    /**
     * @Author      : Theory
     * @Description : 删除数据库中学生信息
     * @Param       : [studentId] -- 学生账号
     */
    @PreAuthorize("hasRole('manager')")
    @ApiOperation(value = "删除数据库中学生信息", notes = "删除数据库中学生信息",httpMethod = "DELETE")
    @ApiParam(name = "studentId",value = "学生账号")
    @DeleteMapping("")
    public void deleteLesson(@RequestParam("phone") long phone){
        studentService.deleteStudent(phone);
    }


    @PreAuthorize("hasRole('manager')")
    @ApiOperation(value = "分页获取所有课程管理员", notes = "分页获取所有课程管理员",httpMethod = "GET")
    @GetMapping("/lessonManagerPages")
    public Page<StudentEntity> getLessonManagerPages(@PageableDefault(size = 5, sort = {"academyName"}, direction = Sort.Direction.DESC)@ApiParam(value = "分页信息") Pageable pageable) {

        Specification<StudentEntity> specification = specUtil.createLessonManagerSpecification();
        return studentService.getAll(specification,pageable);
    }

    @PreAuthorize("hasRole('manager')")
    @ApiOperation(value = "分页根据姓名获取所有课程管理员", notes = "分页根据姓名获取所有课程管理员",httpMethod = "GET")
    @GetMapping("/lessonManagerPagesByName")
    public Page<StudentEntity> getLessonManagerPagesByName(@PageableDefault(size = 5, sort = {"academyName"}, direction = Sort.Direction.DESC)@ApiParam(value = "分页信息") Pageable pageable,
                                                           @RequestParam(value = "realName")@ApiParam(value = "课程管理员名") String realName) {

        Specification<StudentEntity> specification = specUtil.createLessonManagerSpecificationByName(realName);
        return studentService.getAll(specification,pageable);
    }

    @PreAuthorize("hasRole('manager')")
    @ApiOperation(value = "分页根据学校获取所有课程管理员", notes = "分页根据学校获取所有课程管理员",httpMethod = "GET")
    @GetMapping("/lessonManagerPagesBySchool")
    public Page<StudentEntity> getLessonManagerPagesBySchool(@PageableDefault(size = 5, sort = {"academyName"}, direction = Sort.Direction.DESC)@ApiParam(value = "分页信息") Pageable pageable,
                                                             @RequestParam(value = "schoolName")@ApiParam(value = "学校名") String schoolName) {

        Specification<StudentEntity> specification = specUtil.createLessonManagerSpecificationBySchool(schoolName);
        return studentService.getAll(specification,pageable);
    }




    @PreAuthorize("hasRole('student')")
    @ApiOperation(value = "获取学生的技能图",notes = "获取学生的技能图",httpMethod = "GET")
    @GetMapping("/skill")
    public void getSkillImg(@RequestParam(value = "phone") long phone){
        studentService.getSkillImg(phone);
    }




    @PreAuthorize("hasRole('manager')")
    @ApiOperation(value = "获取各项图表数据",notes = "获取各项图表数据",httpMethod = "GET")
    @GetMapping("/allGraph")
    public void getAllGraph(){
        studentService.getAllGraph();
    }



    @PreAuthorize("hasRole('student')")
    @ApiOperation(value = "上传学生图片",notes = "上传学生图片",httpMethod = "POST")
    @PostMapping("/imgUpload")
    public void uploadImg(@RequestParam("img")@ApiParam(value = "img") MultipartFile file,
                          @RequestParam("fileName")@ApiParam(value = "fileName") String fileName) {
        try {
            File path2 = new File(ResourceUtils.getURL("classpath:static").getPath().replace("%20"," ").replace('/', '\\'));
            if(!path2.exists()) path2 = new File("");
            File upload2 = new File(path2.getAbsolutePath(),"img/student/");
            if(!upload2.exists()) upload2.mkdirs();
            String path=upload2.getAbsolutePath()+"/"+fileName;
            File img = new File(path);
            if(!img.exists())
                img.createNewFile();//不存在则创建新文件
            file.transferTo(img);
            StudentEntity oldStu = studentService.getStuById(fileName);
            oldStu.setImgLink("../img/student/"+fileName);
            studentService.insertStudent(oldStu);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
