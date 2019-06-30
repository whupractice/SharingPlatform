var app = angular.module('myApp');

/**
 * @Author      : Theory
 * @Description : 登陆注册控制器
 * @type        : Controller
 */
app.controller('categoryCtrl', function ($scope, $http) {


    $scope.selectLesson = null;//选择的课程


    $scope.currentPage = 1;//当前页数
    $scope.totalPage = 0; // 总页数 （根据 总记录数、每页记录数 计算 ）
    $scope.currentStatus = "全部";
    $scope.currentSubject = "全部";
    $scope.pages = [];



    $scope.init = function () {
        selectPage(1);
    };



    //选择页数
    $scope.selectPage = function (page) {
        if ($scope.totalPage != 0 && (page < 1 || page > $scope.totalPage))
            return;
        console.log('当前页数是：' + page);
        document.getElementById('page').innerHTML = page;
        $scope.currentPage = page;
        $scope.getLessons();
    };

    //上一页
    $scope.prevPage = function () {
        $scope.selectPage($scope.currentPage-1);
    };

    //下一页
    $scope.nextPage = function () {
        $scope.selectPage($scope.currentPage+1);
    };


    //修改状态
    $scope.setStatus = function (status) {
        $scope.currentStatus = status;
        $scope.getLessons();
    };


    //修改类别
    $scope.setSubject = function (subject) {
        $scope.currentSubject = subject;
        $scope.getLessons();
    };


    //发送请求
    $scope.getLessons = function () {
        $http({
            method: 'GET',
            url: url,
            params:{

            }
        }).then(function successCallback(response) {
            $scope.selectLesson = response.data;//获取返回的课程
        });
    };

    /**
     * @Author      : Theory
     * @Description : 跳转到课程详情页
     * @Param       : 被点击的课程
     */
    $scope.goDetail = function (lesson) {
        $state.go('courseinfo', {
            "lesson": lesson
        });
    }

});



