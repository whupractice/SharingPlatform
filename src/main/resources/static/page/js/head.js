var API_index = angular.module('myApp');

/**
 * @Author      : Theory
 * @Description : 主页面控制器
 * @type        : Controller
 */
API_index.controller("headCtrl", function ($scope, $http, $state,$interval) {



    $scope.currentUser = null;
    $scope.messageNum = 0;

    /**
     * @Author      : Theory
     * @Description : 跳转到登录页
     */
    $scope.goLogin = function () {
        $state.go('login');
    };


    /**
      * @Author      : Theory
      * @Description : 初始化导航栏
      */
    $scope.initHead = function () {
        //获取全局保留的token和phone
        var token = window.localStorage.getItem('token');
        var phone = window.localStorage.getItem('phone');
        if(token!="") {
            //获取用户信息
            $http({
                method: 'GET',
                url: '/student/info',
                headers: {
                    'Authorization': token
                },
                params: {
                    "phone": phone
                }
            }).then(function successCallback(response) {
                $scope.currentUser = response.data;
            });
            //获取消息数量
            $scope.getMessageNum();
        }
    };


    //若为学生，去往学生界面
    $scope.goStudent = function(){
        if($scope.isStu()){
            $state.go('student');
        }
    };


    //每一秒刷新页面，获取用户信息
    $interval(function(){
            $scope.initHead();
    },1000);


    //判断角色类型
    $scope.hasUser = function () {
        if($scope.currentUser.nickName.length == 0)//没有用户
            return 0;
        else if($scope.currentUser.isManager == 1)
            return 1;
    };

    //是否是系统管理员
    $scope.isSysManager = function () {
        return $scope.currentUser.isManager == 1;
    };



    //是否是普通学生
    $scope.isStu = function () {
        return ($scope.currentUser.isManager == 0 && $scope.currentUser.isLessonManager==0);
    };



    //是否是课程管理员
    $scope.isLeManager = function () {
        return $scope.currentUser.isLessonManager == 1;
    };


    //获取学生消息数量
    $scope.getMessageNum = function () {
        var token = window.localStorage.getItem('token');
        var judge = $scope.isStu();
        if(judge==true) {
            var phone = $scope.currentUser.phone;
            $http({
                method: 'GET',
                url: "/message/phone",
                headers: {
                    'Authorization': token
                },
                params: {
                    "phone": phone
                }
            }).then(function successCallback(response) {
                $scope.messageNum = response.data.length;
            });
        }
    };

});



