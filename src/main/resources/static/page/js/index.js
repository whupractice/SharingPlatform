var API_index = angular.module('myApp',['ui.router']);
API_index.config(function ($stateProvider,$urlRouterProvider) {

    $urlRouterProvider.otherwise('/main');
    $stateProvider
        .state('main',{
            url: '/main',
            templateUrl: '/page/main.html'
        })
        .state('courseinfo:courseId',{
            params: {"course":null},//转到课程详情页面时，传递参数[课程信息]
            url: '/couserinfo',
            templateUrl: '/page/courseinfo.html'
        })
        .state('schoolCourse', {
            url: '/schoolCourse',
            templateUrl: '/page/schoolCourse.html'
        }).state('login', {
            url: '/login',
            templateUrl: '/page/login.html'
        });

});
