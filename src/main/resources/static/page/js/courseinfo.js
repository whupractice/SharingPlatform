var API_index = angular.module('myApp');

/**
 * @Author      : Theory
 * @Description : 主页面控制器
 * @type        : Controller
 */
API_index.controller("courseinfoCtrl", function ($scope, $http, $state,$stateParams) {


    $scope.lesson = null;

    $scope.TL = null;


    $scope.now = 1;//当前位置


    $scope.teachers = [];

    /**
      * @Author      : Theory
      * @Description : 初始化课程的信息
      */
    $scope.initCourseInfo = function(){
        $scope.lesson = $stateParams.lesson;//接收传来课程信息
       $scope.getTeachers();
    };



    /**
      * @Author      : Theory
      * @Description : 获取此门课程的学习人数
      */
    $scope.getStuNum = function () {
        //TODO
    };


    //去往学校界面
    $scope.goSchool2 = function () {
        $http({
            method: 'GET',
            url: "/school/schoolName",
            params:{
                "schoolName" : $scope.lesson.schoolName
            }
        }).then(function successCallback(response) {
            let school = response.data;
            $state.go('schoolcourse',{
                "school": school
            })
        });
    };

    //跳转
    $scope.jump = function(page){
        return $scope.now = page;
    };
    //显示课程详情内容
    $scope.CoursePage = function(){
      return $scope.now == 1;
    };
    //显示评论区
    $scope.CommentPage = function(){
        return $scope.now == 2;
    };


    $scope.getTeachers = function () {
        let lessonId = $scope.lesson.lessonId;//获取课程id
        $http({
            method: 'GET',
            url: '/tl/id',
            params:{
                "lessonId" : lessonId
            }
        }).then(function successCallback(response) {
            $scope.TL = response.data;
            for(let i = 0;i<$scope.TL.length;i++){
                $scope.getTeachers_($scope.TL[i]);
            }
        });
    };


    $scope.getTeachers_ = function (tl) {
        $http({
            method: 'GET',
            url: '/teacher/id',
            params:{
                "id" : tl.teacherId // id必须从接口文档里看
            }
        }).then(function successCallback(response) {
            let t = response.data;
            $scope.teachers.push(t);
        });
    }

});



