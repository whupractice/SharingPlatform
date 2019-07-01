var API_index = angular.module('myApp',['ui.router']);

API_index.factory('Data',function () {

    //定义factory返回对象(学生)
    var student = {};

    var _set = function (data) {
        student = data;
    };

    var _get = function () {
        return student;
    };

    // Public APIs
    student.set = _set;
    student.get = _get;

    // 在controller中通过调set()和get()方法可实现提交或获取参数的功能
    return student;

});


API_index.config(function ($stateProvider,$urlRouterProvider) {


    /**
      * @Author      : Theory
      * @Description : 设置路由
      */
    $urlRouterProvider.otherwise('/main');//默认页面为主页面
    $stateProvider
        .state('main',{
            url: '/main',
            templateUrl: '/page/main.html'
        })
        .state('courseinfo',{
            url: '/couserinfo',
            params:{"lesson": null},
            templateUrl: '/page/courseinfo.html'
        })
        .state('schoolcourse', {
            url: '/schoolcourse',
            params:{"school": null},
            templateUrl: '/page/schoolcourse.html'
        }).state('login', {
            url: '/login',
            templateUrl: '/page/login.html'
        }).state('category', {
            url: '/category',
            templateUrl: '/page/category.html'
        }).state('schools', {
            url: '/schools',
            templateUrl: '/page/schools.html'
        }).state('teacher',{
            url: '/teacher',
            templateUrl: '/page/teacher.html',
            params:{"teacher": null}
        });

});
