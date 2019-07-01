var app = angular.module('myApp');

/**
 * @Author      : Theory
 * @Description : 学校信息控制器
 * @type        : Controller
 */
app.controller('schoolcourseCtrl', function ($scope, $http, $stateParams,$state) {


    $scope.school = null;//当前学校

    $scope.selectLesson = null;//选择的课程


    $scope.currentPage = 1;//当前页数
    $scope.totalPage = 0;//总页数
    $scope.pages = [];



    $scope.initSchool = function () {
        $scope.school = $stateParams.school;//获取传来的学校信息
        $scope.selectPage_(1);
    };



    //选择页数
    $scope.selectPage_ = function (page) {
        if ($scope.totalPage != 0 && (page < 1 || page > $scope.totalPage))
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
        $http({
            method: 'GET',
            url: "lesson/pagesBySchoolName?page="+$scope.currentPage,
            params:{
                "schoolName" : $scope.school.schoolName
            }
        }).then(function successCallback(response) {
            $scope.selectLesson = response.data.content;//获取返回的课程
            $scope.totalPage = response.data.totalPages-1;//获取最大页数
            $scope.pages = [];
            if($scope.totalPage>5) {
                let start = ($scope.currentPage>=3) ? $scope.currentPage-2 : 1;
                let end = ($scope.currentPage<=$scope.totalPage-2) ? start+4 : $scope.totalPage;
                for(let i = start;i<=end;i++)
                    $scope.pages.push(i);
            }else{
                for(let i = 1;i<=$scope.totalPage;i++)
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
        $state.go('courseinfo', {
            "lesson": lesson
        });
    }

});



