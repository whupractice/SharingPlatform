var app = angular.module('myApp');

/**
 * @Author      : Theory
 * @Description : 管理员页面控制器
 * @type        : Controller
 */
app.controller('administerCtrl', function ($scope, $http, $state) {


    $scope.lesson = null;

    $scope.teacher = null;


    //TODO 搜索课程(依据关键字)
    $scope.searchLesson = function () {
        $http({
            method: 'GET',
            url: '/lesson/keyword',
            params:{
                //TODO 关键字赋值
            }
        }).then(function successCallback(response) {
            $scope.lesson = response.data;
        })
    };
    
    //TODO 增加课程
    $scope.addLesson = function () {
        //TODO 课程名、学校名、学分、开始时间、结束时间、图片、是否精品、类别、学历
    };


    //TODO 更新课程
    $scope.updateLesson = function () {
        //TODO 课程名、学校名、学分、开始时间、结束时间、图片、是否精品、类别、学历
    };


    //TODO 删除课程
    $scope.deleteLesson = function (id) {
        $http({
            method: 'DELETE',
            url: '/lesson',
            params:{
                lessonId: id
            }
        }).then(function successCallback(response) {
            if(response.status == 200){
                alert("删除成功！");
            }
        })
    };


    //TODO 搜索老师（按照关键字搜索）
    $scope.searchTeacher = function () {
        $http({
            method: 'GET',
            url: '/teacher/keyword',
            params:{
                //TODO 关键字赋值
            }
        }).then(function successCallback(response) {
            $scope.lesson = response.data;
        })
    };



});



