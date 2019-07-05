var app = angular.module('myApp');

/**
 * @Author      : Theory
 * @Description : 学生控制器
 * @type        : Controller
 */
app.controller('studentCtrl', function ($scope, $http, $state,Data) {   //Data是全局变量，保存当前用户


    $scope.currentStudent = null;

    $scope.nowPage = 1;

    $scope.nowLesson = null;

    $scope.selectL = null;//当前选中的课程


    //初始化学生信息
    $scope.initStudent = function () {
        $scope.currentStudent = Data.get();//获取当前学生信息
        $scope.getLessonByPhone();//获取当前课程
    };


    //跳转页面
    $scope.jump_ = function (page) {
        $scope.nowPage = page;
    };


    //第一面
    $scope._page_1_ = function () {
        return $scope.nowPage == 1;
    };
    //第二面
    $scope._page_2_ = function () {
        return $scope.nowPage == 2;
    };
    //第三面
    $scope._page_3_ = function () {
        return $scope.nowPage == 3;
    };
    //第四面
    $scope._page_4_ = function () {
        return $scope.nowPage == 4;
    };

    //更新学生信息
    $scope.updateStuInfo = function () {
        let phoneNumber = $scope.currentStudent.phone;
        let realName=$scope.currentStudent.realName;
        let nickName = $('#nickName').val();
        let pwd = $scope.currentStudent.pwd;
        let sex = $('#sex').find('option:selected').text();
        let email = $('#email').val();
        let birth = $('#birth').val();
        let introduction = $('#introduction').val();

        $http({
            method: 'PUT',
            url: '/student',
            data:{
                "phone": phoneNumber,
                "birth": birth,
                "email": email,
                "pwd":pwd,
                "realName":realName,
                "nickName": nickName,
                "introduction":introduction,
                "sex": sex
            }
        }).then(function successCallback(response) {
            if(response.status == 200){
                alert("修改成功！");
            }else{
                alert("修改失败!");
            }
        })

    };


    //改变密码
    $scope.changePwd = function () {
        let primPwd = $('#primPwd').val();
        let newPwd = $('#newPwd').val();
        let confirmPwd = $('#confirmPwd').val();

        if(primPwd == newPwd){
            alert("前后密码一致，请重新输入！");
            return;
        }else if(newPwd != confirmPwd){
            alert("二次密码不一致！请重新确认密码！");
            return;
        }else{
            $http({
                method: 'POST',
                url: '/student/login',
                data:{
                    "phone": $scope.currentStudent.phone,
                    "pwd": primPwd
                }
            }).then(function successCallback(response) {
                if(response.data.length != 0){
                    $http({
                        method: 'PUT',
                        url: '/student',
                        data:{
                            "phone": $scope.currentStudent.phone,
                            "birth": $scope.currentStudent.birth,
                            "email": $scope.currentStudent.email,
                            "pwd":newPwd,
                            "realName":$scope.currentStudent.realName,
                            "nickName": $scope.currentStudent.nickName,
                            "introduction":$scope.currentStudent.introduction,
                            "sex": $scope.currentStudent.sex
                        }
                    }).then(function successCallback(response) {
                        if(response.status == 200){
                            alert("修改成功！");
                        }else{
                            alert("修改失败!");
                        }
                    })
                }
                else {
                    alert("用户名或者密码错误")
                }
            })
        }

    };


    //根据学生电话号码查询所选课程
    $scope.getLessonByPhone = function () {
        let phone = $scope.currentStudent.phone;
        $http({
            method: 'GET',
            url: '/sl/getLessonByStuId',
            params:{
                "stuId": phone
           }
        }).then(function successCallback(response) {
            $scope.nowLesson = response.data;
        })
    };


    //进入课程
    $scope.enterLesson = function (x) {
        $state.go('courseinfo',{
            "lesson": x
        })
    };




    //删除所选课程
    $scope.deleteLesson_ = function () {
        $http({
            method: 'DELETE',
            url: '/sl/stuId/lessonId',
            params:{
                "stuId": $scope.currentStudent.phone,
                "lessonId": $scope.selectL.lessonId
            }
        }).then(function successCallback(response) {
            if(response.status==200){
                $('#deleteL_modal').modal('hide');
                $scope.getLessonByPhone();
            }
            else {
                alert("删除失败！");
            }
        })
    };



    //绑定当前选中课程
    $scope.bindL = function (x) {
        $scope.selectL = x;
    };



    //给课程打分
    $scope.score = function (x) {

    };
});
