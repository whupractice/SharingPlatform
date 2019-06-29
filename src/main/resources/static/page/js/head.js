var API_index = angular.module('myApp');

/**
 * @Author      : Theory
 * @Description : 主页面控制器
 * @type        : Controller
 */
API_index.controller("headCtrl", function ($scope, $http, $state) {


    /**
     * @Author      : Theory
     * @Description : 跳转到登录页
     */
    $scope.goLogin = function () {
        $state.go('login');
    }

});



