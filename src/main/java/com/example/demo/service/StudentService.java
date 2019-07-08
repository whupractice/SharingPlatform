package com.example.demo.service;

import com.example.demo.entity.StudentEntity;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import springfox.documentation.annotations.Cacheable;

import java.util.*;

/**
 * @ Author     ：Theory
 * @ Description：学生逻辑服务类
 */

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    private Map<String, UserDetails> tokenMap = new HashMap<>();

    @Cacheable("user")
    public UserDetails getUserFromToken(String token) {
        if(token == null) {
            return null;
        }
        return tokenMap.get(token);
    }

    /**
      * @Author      : Theory
      * @Description : 判断账号密码是否正确
      * @Param       : [phone,id] -- 用户名、密码
      * @return      : 是否正确
      */
    public String judgeLogin(long phone,String pwd){
        StudentEntity s =studentRepository.getStuById(phone);
        UserDetails userDetails;
        if(s.getPwd().equals(pwd)){
            if(Boolean.parseBoolean(String.valueOf(s.getIsManager()))){
                userDetails = createUser(String.valueOf(phone),pwd,new String[]{"manager","lessonManager","student"});
            } else if(Boolean.parseBoolean(String.valueOf(s.getIsLessonManager()))){
                userDetails = createUser(String.valueOf(phone),pwd,new String[]{"lessonManager","student"});
            } else {
                userDetails = createUser(String.valueOf(phone),pwd,new String[]{"student"});
            }
            String token = UUID.randomUUID().toString();
            tokenMap.put(token, userDetails);
            return token;
        }else {
            return null;
        }
    }

    public void logout(String token) {
        tokenMap.remove(token);
    }


    public Object getNickNameByPhone(String phone) {
        Long newPhone = Long.parseLong(phone);
        return studentRepository.getNickNameByPhone(newPhone);
    }



    /**
     * @Author      : QinYingran
     * @Description : 分页条件查询学生或管理员
     * @Param       : [specification, pageable]
     * @return      : org.springframework.data.domain.Page<com.example.demo.entity.StudentEntity>
     */
    public Page<StudentEntity> getAll(Specification<StudentEntity> specification, Pageable pageable) {
        return studentRepository.findAll(specification,pageable);
    }


    /**
      * @Author      : QinYingran
      * @Description : 根据id获取学生
      * @Param       : [phone]
      * @return      : com.example.demo.entity.StudentEntity
      */
    public StudentEntity getStuById(String phone){
        Long newPhone = Long.parseLong(phone);
        return studentRepository.getStuById(newPhone);
    }

    /**
      * @Author      : QinYingran
      * @Description : 根据nickName获取学生
      * @Param       : [nickName]
      * @return      : com.example.demo.entity.StudentEntity
      */
    public StudentEntity getStuByNickName(String nickName) {
        return studentRepository.getStuByNickName(nickName);
    }

    /**
      * @Author      : QinYingran
      * @Description : 根据schoolName获取学生
      * @Param       : [schoolName]
      * @return      : java.util.List<com.example.demo.entity.StudentEntity>
      */
    public List<StudentEntity> getStuBySchoolName(String schoolName) {
        return studentRepository.getStuBySchoolName(schoolName);
    }


    /**
      * @Author      : QinYingran
      * @Description : 注册学生
      * @Param       : [stu]
      * @return      : boolean
      */
    public String register(StudentEntity stu){
        stu.setNickName(String.valueOf(stu.getPhone()));
        StudentEntity stu1 = studentRepository.getStuById(stu.getPhone());
        StudentEntity stu2 = studentRepository.getStuByNickName(stu.getNickName());
        if(stu1==null && stu2==null) {
            studentRepository.save(stu);//向数据库中插入学生
            return judgeLogin(stu.getPhone(),stu.getPwd());
        }
        return null;
    }



    /**
      * @Author      : Theory
      * @Description : 获取数据库中所有学生
      * @return      : 所有学生
      */
    public List<StudentEntity> getAllStudent(){
        return studentRepository.findAll();
    }

    public List<StudentEntity> getAllManager() {
        return studentRepository.getAllManager();
    }



    /**
      * @Author      : QinYingran
      * @Description : 更新学生
      * @Param       : [stu]
      * @return      : boolean
      */
    public boolean insertStudent(StudentEntity stu){
        StudentEntity stu1 = studentRepository.getStuById(stu.getPhone());
        StudentEntity stu2 = studentRepository.getStuByNickName(stu.getNickName());
        if(stu1!=null) {
            if(stu2!=null && stu2.getNickName()!=stu1.getNickName()){
                return false;
            }
            studentRepository.save(stu);//向数据库中插入学生
            return true;
        }
        return false;
    }



    /**
      * @Author      : Theory
      * @Description : 根据学生账号删除学生id
      * @Param       : [id] -- 学生账号
      */
    public void deleteStudent(long phone){
        studentRepository.deleteById(phone);
    }



    private UserDetails createUser(String userName, String password, String[] roles){
        return new UserDetails() {

            //private static final long serialVersionUID = 6905138725952656074L;

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                Collection<GrantedAuthority> authorities = new ArrayList<>();

//                //这是增加了一种名为query的权限，可以使用 @hasAuthority("query") 来判断
//                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("query");
//                authorities.add(authority);

                //这是增加到xxx角色，可以用hasRole("xxx")来判断；需要注意所有的角色在这里增加时必须以ROLE_前缀，使用时则没有ROLES_前缀
                for(String role : roles) {
                    SimpleGrantedAuthority sga = new SimpleGrantedAuthority("ROLE_" + role);
                    authorities.add(sga);
                }
                return authorities;
            }

            @Override
            public String getPassword() {
                return password;
            }

            @Override
            public String getUsername() {
                return userName;
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }

}
