var app = angular.module('myApp');

/**
 * @Author      : Theory
 * @Description : 导航栏控制器
 * @type        : Controller
 */
app.controller('headCtrl', function ($scope, $http, $state) {


    /**
      * @Author      : Theory
      * @Description : 前往登陆或注册页面
      */
    $scope.goLogin = function (){
        $state.go('login');
    }

});



