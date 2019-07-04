var app = angular.module('myApp');

/**
 * @Author      : Theory
 * @Description : 学生控制器
 * @type        : Controller
 */
app.controller('studentCtrl', function ($scope, $http, $state,Data) {   //Data是全局变量，保存当前用户


    $scope.currentStudent = null;

    $scope.nowPage = 1;


    //初始化管理员信息
    $scope.initstudent = function () {
        $scope.currentStudent = Data.get();//获取当前管理员信息
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


});
