var app = angular.module('myApp');

/**
 * @Author      : Theory
 * @Description : 登陆注册控制器
 * @type        : Controller
 */
app.controller('loginCtrl', function ($scope, $http, $state,Data) {


    $scope.judgeLog = function(){
        var r = document.getElementsByName("identity");
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
        let user = parseInt($('#acct').val());
        let pwd = $('#pwd').val();

        $http({
            method: 'POST',
            url: '/student/login',
            data:{
                "studentId": user,
                "pwd" : pwd
            }
        }).then(function successCallback(response) {
            if(!!response.data){
                Data.set(response.data);
                $state.go('main');//跳转主页面
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
        let nickName = $('#acct').val();
        let pwd = $('#pwd').val();
        $http({
            method: 'POST',
            url: '/student/register',
            data:{
                "nickName": nickName,
                "pwd" : pwd
            }
        }).then(function successCallback(response) {
            if(response.data>0){
                let userAccount = response.data;
                alert("注册成功！请记住：您的账号为 " +userAccount);
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
        let user = parseInt($('#acct').val());
        let pwd = $('#pwd').val();

        $http({
            method: 'POST',
            url: '/student/login/manager',
            data:{
                "studentId": user,
                "pwd" : pwd
            }
        }).then(function successCallback(response) {
            if(response.data==true){
                $state.go('main');//跳转到主页面
            }else{
                alert("用户名或密码错误！");
            }
        });
    }
});



