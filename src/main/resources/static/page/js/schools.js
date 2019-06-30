var app = angular.module('myApp');

/**
 * @Author      : Theory
 * @Description : 学校集合控制器
 * @type        : Controller
 */
app.controller('schoolsCtrl', function ($scope, $http, $state) {


    $scope.schoools = null;

    //从数据库中获取所有学校信息
    $scope.initSchools = function () {
        $http({
            method: 'GET',
            url: '/school'
        }).then(function successCallback(response) {
            $scope.schoools = response.data;
        });
    };


    //进入学校详情页schoolcourse
    $scope.goSchool = function (school) {
        $state.go('schoolcourse',{
            "school": school
        })
    }
});



