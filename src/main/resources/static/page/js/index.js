var API_index = angular.module('myApp',['ui.router']);
API_index.config(function ($stateProvider,$urlRouterProvider) {


    /**
      * @Author      : Theory
      * @Description : 设置路由
      */
    $urlRouterProvider.otherwise('/main');//默认页面为主页面
    $stateProvider
        .state('main',{
            url: '/main',
            params:{"studentId":null},
            templateUrl: '/page/main.html'
        })
        .state('courseinfo',{
            url: '/couserinfo',
            params:{"lesson": null},
            templateUrl: '/page/courseinfo.html'
        })
        .state('schoolCourse', {
            url: '/schoolCourse',
            templateUrl: '/page/schoolCourse.html'
        }).state('login', {
            url: '/login',
            templateUrl: '/page/login.html'
        }).state('category', {
            url: '/category',
            templateUrl: '/page/category.html'
        }).state('schoolcourse', {
            url: '/schoolcourse',
            templateUrl: '/page/schoolcourse.html'
        });

});
