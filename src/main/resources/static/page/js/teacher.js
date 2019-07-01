var app = angular.module('myApp');

/**
 * @Author      : Theory
 * @Description : 教师控制器
 * @type        : Controller
 */
app.controller('teacherCtrl', function ($scope, $http, $stateParams,$state) {//$stateParams别的页面传来的参数

    $scope.teacher = null;//教师

    $scope.t_lesson = null;//课程


    // $http({
    //     method: 'GET',
    //     url: '/teacher/id',
    //     params:{
    //         "id" : $scope.teacher.teacherId // id必须从接口文档里看
    //     }
    // }).then(function successCallback(response) {
    //     $scope.teacher = response.data;
    // });


    $scope.initTeacher = function () {
        $scope.teacher = $stateParams.teacher;//获取传来的老师
        $http({
            method: 'GET',
            url: '/teacher/id',
            params:{
                "id" : $scope.teacher.teacherId
            }
        }).then(function successCallback(response) {
            $scope.teacher = response.data;//获取返回的数据
        });
    }



});



