var API_index = angular.module('myApp',['ui.router']);
API_index.config(function ($stateProvider,$urlRouterProvider) {

    $urlRouterProvider.otherwise('/main');
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
        });

});
