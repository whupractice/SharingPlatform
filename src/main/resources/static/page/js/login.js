var app = angular.module('myApp');

/**
 * @Author      : Theory
 * @Description : 登陆注册控制器
 * @type        : Controller
 */
app.controller('loginCtrl', function ($scope, $http, $state) {   //Data是全局变量，保存当前用户


    $scope.currentToken = "";//用户token

    $scope.currentUser = null;






    /**
      * @Author      : Theory
      * @Description : 登录
      * @version     : 加了权限
      */
    $scope.judgeLog = function(){
        var user = $('#acct').val();
        var pwd = $('#pwd').val();
        $http({
            method: 'POST',
            url: '/student/login',
            data:{
                "phone": user,
                "pwd": pwd
            }
        }).then(function successCallback(response) {
            if(response.status==200 && response.data.length!=0){
                $scope.currentToken = response.data.token;//当前用户token
                window.localStorage.setItem('token',$scope.currentToken);//保存用户当前token至localStorage
                window.localStorage.setItem('phone',user);//保存用户电话到localStorage
                $state.go('main');
            }
            else {
                alert("用户名或者密码错误")
            }
        })
    };


    $scope.register = function(){

        var user = $('#phone_number').val();
        var pwd = $('#code1').val();
        if(user.length == 0 || pwd.length == 0){
            alert("用户名和密码不能为空！")
            return;
        }
        if ( pwd == $('#code2').val()){

            $http({
                method: 'POST',
                url: '/student/register',
                data: {
                    "phone": user,
                    "pwd": pwd,
                    "imgLink": "../img/user.jpg"
                }
            }).then(function successCallback(response) {
                if (response.data != null) {
                    alert('账号注册成功')
                } else {
                    alert("账号注册失败")
                }

            })
        }
        else {
            alert("两次密码输入不一致！")
        }
    };




});



