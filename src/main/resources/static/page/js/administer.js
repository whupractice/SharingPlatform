var app = angular.module('myApp');

/**
 * @Author      : Theory
 * @Description : 管理员页面控制器
 * @type        : Controller
 */
app.controller('administerCtrl', function ($scope, $http, $state,Data) {

    $scope.currentSytemManager = null;//当前系统管理员

    $scope.nowPage = 1;

    $scope.allSystemManager = null;//所有的系统管理员
    $scope.allLessonManager = null;//所有的课程管理员

    $scope.bindSysMan = null;//当前选中的系统管理员

    //初始化管理员信息
    $scope.initManager = function () {
        $scope.getAllSystemManagers();//获取所有管理员信息
        $scope.currentManager = Data.get();//获取当前管理员信息
    };


    //跳转页面
    $scope.jump = function (page) {
        $scope.nowPage = page;
    };


    //第一面
    $scope.page_1 = function () {
        return $scope.nowPage == 1;
    };
    //第二面
    $scope.page_2 = function () {
        return $scope.nowPage == 2;
    };
    //第三面
    $scope.page_3 = function () {
        return $scope.nowPage == 3;
    };


    //获取所有的系统管理员
    $scope.getAllSystemManagers = function () {
        $http({
            method: 'GET',
            url: '/student/manager'
        }).then(function successCallback(response) {
            $scope.allSystemManager = response.data;
        })
    };


    //绑定当前系统管理员
    $scope.bindingSys = function (x) {
        $scope.bindSysMan = x;
    };


    //删除当前选中的系统管理员
    $scope.deleteSys = function (x) {
        $http({
            method: 'DELETE',
            url: '/student',
            params:{
                "phone": x.phone
            }
        }).then(function successCallback(response) {
            if(response.status == 200){
                $scope.getAllSystemManagers();
            }
        })
    };

});



