var app = angular.module('myApp');

/**
 * @Author      : Theory
 * @Description : 登陆注册控制器
 * @type        : Controller
 */
app.controller('headCtrl', function ($scope, $http, $state) {



    /**
      * @Author      : Theory
      * @Description : 用户登陆
      */
    $('#logBtn').on('click',function () {
        let user = document.getElementById("acct");
        let pwd = document.getElementById("pwd");

        $http({
            method: 'POST',
            url: '/student/login',
            data:{
                "user": user,
                "pwd" : pwd
            }
        }).then(function successCallback(response) {
            if(response.data==true){
               $state.go('main');
            }else{
                alert("用户名或密码错误！");
            }
        });
    })
});



