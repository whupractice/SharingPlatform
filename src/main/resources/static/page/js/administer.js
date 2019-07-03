var app = angular.module('myApp');

/**
 * @Author      : Theory
 * @Description : 管理员页面控制器
 * @type        : Controller
 */
app.controller('administerCtrl', function ($scope, $http, $state,Data) {

    $scope.currentSytemManager = null;//当前系统管理员

    $scope.nowPage = 1;

    $scope.allSystemManager = null;//所有的系统管理员
    $scope.allLessonManager = null;//所有的课程管理员

    $scope.totalP = 1;//课程管理员总页数
    $scope.currentP = 1;//课程管理员当前页数
    $scope.Ps = [];

    $scope.bindSysMan = null;//当前选中的系统管理员




    //初始化管理员信息
    $scope.initManager = function () {
        $scope.getAllSystemManagers();//获取所有管理员信息
        $scope.getAllLessonManagers();//获取所有课程管理员信息
        $scope.currentManager = Data.get();//获取当前管理员信息
    };


    //跳转页面
    $scope.jump = function (page) {
        $scope.nowPage = page;
    };


    //第一面
    $scope.page_1 = function () {
        return $scope.nowPage == 1;
    };
    //第二面
    $scope.page_2 = function () {
        return $scope.nowPage == 2;
    };
    //第三面
    $scope.page_3 = function () {
        return $scope.nowPage == 3;
    };


    //获取所有的系统管理员
    $scope.getAllSystemManagers = function () {
        $http({
            method: 'GET',
            url: '/student/manager'
        }).then(function successCallback(response) {
            $scope.allSystemManager = response.data;
        })
    };


    //分页获取所有的课程管理员
    $scope.getAllLessonManagers = function(){
        let p = $scope.currentP-1;
        $http({
            method: 'GET',
            url: "/student/lessonManagerPages?page="+p
        }).then(function successCallback(response) {
            $scope.allLessonManager = response.data.content;//获取返回的课程
            $scope.totalP = response.data.totalPages;//获取最大页数
            $scope.Ps = [];
            if($scope.totalP>5) {
                let start = ($scope.currentP>=3) ? $scope.currentP-2 : 1;
                let end = ($scope.currentP<=$scope.totalP-2) ? start+4 : $scope.totalP;
                for(let i = start;i<=end;i++)
                    $scope.Ps.push(i);
            }else{
                for(let i = 1;i<=$scope.totalP;i++)
                    $scope.Ps.push(i);
            }
        });
    };

    //选择页号
    $scope.selectP = function (page) {
        if ($scope.totalP == 0 && (page < 1 || page > $scope.totalPage))
            return;
        $scope.currentP = page;
        $scope.getAllLessonManagers();
    };

    //上一页
    $scope.pre = function () {
        $scope.selectP($scope.currentP-1);
    };

    //下一页
    $scope.next = function () {
        $scope.selectP($scope.currentP+1);
    };

    //绑定当前系统管理员
    $scope.bindingSys = function (x) {
        $scope.bindSysMan = x;
    };


    //删除当前选中的系统管理员
    $scope.deleteSys = function (x) {
        $http({
            method: 'DELETE',
            url: '/student',
            params:{
                "phone": x.phone
            }
        }).then(function successCallback(response) {
            if(response.status == 200){
                $scope.getAllSystemManagers();
            }
        })
    };

});



