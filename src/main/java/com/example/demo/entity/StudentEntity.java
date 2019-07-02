package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @ Author     ：Theory
 * @ Description：学生实体类
 */

@Entity
@Table(name = "Student")
public class StudentEntity {

    public StudentEntity() {
    }



    @Id
    private long phone;//电话号码

    private String pwd;//密码

    @NotNull(message = "昵称不能为空")
    private String nickName;//昵称

    private String realName;//真实姓名

    private String birth;//出生年月

    private String sex;//性别

    private String email;//邮箱

    private int isManager;//是否是管理员

    private int isLessonManager;//是否课程管理员

    private String introduction;//个人介绍


    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIsManager() {
        return isManager;
    }

    public void setIsManager(int isManager) {
        this.isManager = isManager;
    }

    public int getIsLessonManager() {
        return isLessonManager;
    }

    public void setIsLessonManager(int isLessonManager) {
        this.isLessonManager = isLessonManager;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
