var app = angular.module('myApp');

/**
 * @Author      : Theory
 * @Description : 学校集合控制器
 * @type        : Controller
 */
app.controller('schoolsCtrl', function ($scope, $http, $state) {

    $scope.schoolNum=0;
    $scope.schools = null;

    //从数据库中获取所有学校信息
    $scope.initSchools = function () {
        $http({
            method: 'GET',
            url: '/school'
        }).then(function successCallback(response) {
            $scope.schools = response.data;
            $scope.schoolNum = $scope.schools.length;
        });
    };


    //进入学校详情页schoolcourse
    $scope.goSchool = function (school) {
        $state.go('schoolcourse',{
            "school": school
        })
    };


    //根据关键字搜索学校
    $scope.searchSchoolName = function () {
        var keyword = $('#searchVal').val();
        $http({
            method: 'GET',
            url: '/school/keyword',
            params:{
                "keyword": keyword
            }
        }).then(function successCallback(response) {
            $scope.schools = response.data;
        });
    };

});



