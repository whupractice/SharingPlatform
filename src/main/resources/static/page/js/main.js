var API_index = angular.module('myApp');

/**
 * @Author      : Theory
 * @Description : 主页面控制器
 * @type        : Controller
 */
API_index.controller("mainCtrl", function ($scope, $http, $state) {

    $scope.intervalId = null;
    $scope.currrentUser = null;//当前用户

    $scope.hotLesson = null;//热门课程
    $scope.hotNum = 8;

    $scope.excellentLesson = null;//精品（推荐）课程
    $scope.excellentNum = 8;




    /**
     * @Author      : Theory
     * @Description : 获取所有热门课程
     */
    $scope.getHotLesson = function () {
        $http({
            method: 'GET',
            url: '/lesson/hot',
            params: {
                lessonNum: $scope.hotNum
            }
        }).then(function successCallback(response) {
            $scope.hotLesson = response.data;
            $scope.getExcellentLesson();
        })
    };


    /**
     * @Author      : Theory
     * @Description : 获取所有精品课程
     */
    $scope.getExcellentLesson = function () {
        $http({
            method: 'GET',
            url: '/lesson/excellent'
        }).then(function successCallback(response) {
            $scope.excellentLesson = response.data;
        })
    };


    /**
      * @Author      : Theory
      * @Description : 初始化主界面
      */
    $scope.initMain = function(){
            $scope.getHotLesson();
    };


    /**
     * @Author      : Theory
     * @Description : 跳转到课程详情页
     * @Param       : 被点击的课程
     */
    $scope.goDetail = function (lesson) {
        window.localStorage.setItem('lessonId',lesson.lessonId);
        $state.go('courseinfo');
    }


});



