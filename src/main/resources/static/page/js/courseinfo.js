var API_index = angular.module('myApp');

/**
 * @Author      : Theory
 * @Description : 主页面控制器
 * @type        : Controller
 */
API_index.controller("courseinfoCtrl", function ($scope, $http, $state,$stateParams) {


    $scope.lesson = null;

$scope.sl=null;//选课信息
    $scope.teacherNum = 0;

    $scope.scores=[];//课程打分

    $scope.now = 1;//当前位置

$scope.Si=null;//Studentinformation
    $scope.teachers = null;

    $scope.currentScore = 4.3;
    $scope.f = 0.3;




    /**
      * @Author      : Theory
      * @Description : 初始化课程的信息
      */
    $scope.initCourseInfo = function(){
        $scope.lesson = $stateParams.lesson;//接收传来课程信息
       $scope.getTeachers();
       //$scope.getScores();
       $scope.getComments();
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
            url: '/tl/getTeacherByLessonId',
            params:{
                "lessonId" : lessonId
            }
        }).then(function successCallback(response) {
            $scope.teachers=response.data;
            $scope.teacherNum = $scope.teachers.length;
        });
    };

//跳转到教师页面
    $scope.jumpT = function (teacher) {
        $state.go('teacher',{
            "teacher" : teacher
        });
    };


    //课程打分
    $scope.getScores=function () {
        var score=Math.floor(4.3);
        $scope.f = 4.3 - score;
        for(let i=0;i<score;i++)
        {
            $scope.scores.push(1);
        }
            };
    $scope.judge=function () {
        return $scope.f>0.5;
    };
    $scope.judge2=function () {
        return $scope.f<=0.5;
    };

//获取评论信息
    $scope.getComments=function () {
        let lessonId = $scope.lesson.lessonId;//获取课程id
        $http({
            method:'GET',
            url:'/sl/lessonId',
            params:{
                "lessonId" : lessonId
            }
        }).then(function successCallback(response) {
            $scope.sl=response.data;
            for(let i = 0;i<$scope.sl.length;i++){
                let sl = $scope.sl[i];
                $http({
                    method: 'GET',
                    url: '/student/info',
                    params: {
                        "phone": sl.phone
                    }
                }).then(function successCallback(response) {
                    ($scope.sl[i])["nickName"] = response.data.nickName;
                })
            }
        })
    }

// //根据获取昵称
//     $scope.getNickname=function () {
//         let lessonId = $scope.lesson.lessonId;//获取课程id
//         $http({
//             method:'GET',
//             url:'/sl/getStudentByLessonId',
//             params:{
//                 "lessonId":lessonId
//             }
//         }).then(function successCallback(response) {
//             $scope.Nn=response.data;
//         })
//
//     }

});





