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
               $state.go('main');
            }else{
                alert("用户名或密码错误！");
            }
        });
    });
    
    
    $('#reBtn').on('click',function () {
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
            if(response.data==true){
                $state.go('main');
            }else{
                alert("用户名或密码错误！");
            }
        });
    });
});



