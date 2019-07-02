var app = angular.module('myApp');

/**
 * @Author      : Theory
 * @Description : 管理员页面控制器
 * @type        : Controller
 */
app.controller('administerCtrl', function ($scope, $http, $state,Data) {

    $scope.currentManager = null;

    $scope.nowPage = 1;


    //初始化管理员信息
    $scope.initManager = function () {
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

});



