var app = angular.module('myApp');

/**
 * @Author      : Theory
 * @Description : 登陆注册控制器
 * @type        : Controller
 */
app.controller('loginCtrl', function ($scope, $http, $state,Data) {   //Data是全局变量，保存当前用户


    $scope.currentUser = null;



    //
    $scope.judgeLog = function(){

        let user = $('#acct').val();
        let pwd = $('#pwd').val();

        $http({
            method: 'POST',
            url: '/student/login',
            //如果swagger文档里参数是body类型，参数用data传递；若为query，参数用params
            data:{
                "phone": user,
                "pwd": pwd
            }
        }).then(function successCallback(response) {
            if(response.status==200 && response.data.length!=0){
                $scope.currentUser = response.data;
                Data.set($scope.currentUser);//设置全局用户为当前的currentuser
                $state.go('main');
            }
            else {
                alert("用户名或者密码错误")
            }
        })
    };


    $scope.register = function(){

        let user = $('#phone_number').val();
        let pwd = $('#code1').val();
        if ( pwd == $('#code2').val()){

            $http({
                method: 'POST',
                url: '/student/register',
                //如果swagger文档里参数是body类型，参数用data传递；若为query，参数用params
                data: {
                    "phone": user,
                    "pwd": pwd
                }
            }).then(function successCallback(response) {
                if (response.data == true) {
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



