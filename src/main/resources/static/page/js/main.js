var app = angular.module('myApp');

/**
 * @Author      : Theory
 * @Description : 主页面控制器
 * @type        : Controller
 */
app.controller('mainCtrl', function ($scope, $http, $state) {

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
            url: '/lesson/excellent',
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
     * TODO : 以后可以将此处优化，直接传递被点击的课程信息，而不是传递id
     */
    $scope.goDetail = function (lesson) {
        $state.go('courseinfo', {
            courseId: lesson.lessonId
        });
    }

});



