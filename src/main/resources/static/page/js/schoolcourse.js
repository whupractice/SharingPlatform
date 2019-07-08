var app = angular.module('myApp');

/**
 * @Author      : Theory
 * @Description : 学校信息控制器
 * @type        : Controller
 */
app.controller('schoolcourseCtrl', function ($scope, $http,$state) {


    $scope.school = null;//当前学校

    $scope.selectLesson = null;//选择的课程


    $scope.currentPage = 1;//当前页数
    $scope.totalPage = 1;//总页数
    $scope.pages = [];


    /**
      * @Author      : Theory
      * @Description : 初始化学校
      */
    $scope.initSchool = function () {
        var schoolId = window.localStorage.getItem('schoolId');
        $http({
            method: 'GET',
            url: "/school/getSchoolById",
            params:{
                "schoolId" : schoolId
            }
        }).then(function successCallback(response) {
            $scope.school = response.data;
        });

        $scope.selectPage_(1);
    };



    //选择页数
    $scope.selectPage_ = function (page) {
        if ($scope.totalPage == 0 && (page < 1 || page > $scope.totalPage))
            return;
        $scope.currentPage = page;
        $scope.getLessons_();
    };

    //上一页
    $scope.prevPage_ = function () {
        $scope.selectPage_($scope.currentPage-1);
    };

    //下一页
    $scope.nextPage_ = function () {
        $scope.selectPage_($scope.currentPage+1);
    };



    //发送请求
    $scope.getLessons_ = function () {
        var p = $scope.currentPage-1;
        $http({
            method: 'GET',
            url: "lesson/pagesBySchoolName?page="+p,
            params:{
                "schoolName" : $scope.school.schoolName
            }
        }).then(function successCallback(response) {
            $scope.selectLesson = response.data.content;//获取返回的课程
            $scope.totalPage = response.data.totalPages;//获取最大页数
            $scope.pages = [];
            if($scope.totalPage>5) {
                var start = ($scope.currentPage>=3) ? $scope.currentPage-2 : 1;
                var end = ($scope.currentPage<=$scope.totalPage-2) ? start+4 : $scope.totalPage;
                for(var i = start;i<=end;i++)
                    $scope.pages.push(i);
            }else{
                for(var i = 1;i<=$scope.totalPage;i++)
                    $scope.pages.push(i);
            }
        });
    };

    /**
     * @Author      : Theory
     * @Description : 跳转到课程详情页
     * @Param       : 被点击的课程
     */
    $scope.goDetail_ = function (lesson) {
        window.localStorage.setItem('lessonId',lesson.lessonId);
        $state.go('courseinfo');
    }

});



