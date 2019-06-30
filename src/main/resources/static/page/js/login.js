var app = angular.module('myApp');

/**
 * @Author      : Theory
 * @Description : 登陆注册控制器
 * @type        : Controller
 */
app.controller('headCtrl', function ($scope, $http, $state) {


    $scope.judgeLog = function(){
        var r = document.getElementsByName("identify");
        if (r[0].checked) {
            $scope.stuLog();//学生登陆
        } else if (r[1].checked) {
            $scope.managerLog();//管理员登陆
        } else {
            alert("请选择至少一个角色！");
        }
    };


    /**
      * @Author      : Theory
      * @Description : 学生登陆
      */
    $scope.stuLog = function () {
        let user = document.getElementById("acct");
        let pwd = document.getElementById("pwd");

        $http({
            method: 'POST',
            url: '/student/login',
            data:{
                "user": user,
                "pwd" : pwd
            },
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            transformRequest: function (obj) {
                var str = [];
                for (var s in obj) {
                    str.push(encodeURIComponent(s) + "=" + encodeURIComponent(obj[s]));
                }
                return str.join("&");
            }
        }).then(function successCallback(response) {
            if(response.data==true){
               $state.go('main',{
                   "studentId": user
               });
            }else{
                alert("用户名或密码错误！");
            }
        });
    };
    

    /**
      * @Author      : Theory
      * @Description : 学生注册
      */
    $scope.stuReg = function () {
        let nickName = document.getElementById("acct");
        let pwd = document.getElementById("pwd");
        $http({
            method: 'POST',
            url: '/student/register',
            data:{
                "user": nickName,
                "pwd" : pwd
            }
        }).then(function successCallback(response) {
            if(response.data>0){
                let userAccount = response.data;
                alert("注册成功！请记住：您的账号为 " +userAccount);
                $scope.go('main',{
                    "studentId": userAccount
                });//跳转主页面
            }else{
                alert("此昵称已被占用！");
            }
        });
    };

    /**
      * @Author      : Theory
      * @Description : 管理员登陆
      */
    $scope.managerLog = function () {
        let user = document.getElementById("acct");
        let pwd = document.getElementById("pwd");

        $http({
            method: 'POST',
            url: '/student/login/manager',
            data:{
                "user": user,
                "pwd" : pwd
            },
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            transformRequest: function (obj) {
                var str = [];
                for (var s in obj) {
                    str.push(encodeURIComponent(s) + "=" + encodeURIComponent(obj[s]));
                }
                return str.join("&");
            }
        }).then(function successCallback(response) {
            if(response.data==true){
                $state.go('main',{
                    "studentId": user
                });
            }else{
                alert("用户名或密码错误！");
            }
        });
    }
});



