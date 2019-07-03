var app = angular.module('myApp');

/**
 * @Author      : Theory
 * @Description : 登陆注册控制器
 * @type        : Controller
 */
app.controller('categoryCtrl', function ($scope, $http, $state) {


    $scope.selectLesson = null;//选择的课程


    $scope.currentPage = 1;//当前页数
    $scope.totalPage = 1; // 总页数 （根据 总记录数、每页记录数 计算 ）
    $scope.currentStatus = "全部";
    $scope.currentSubject = "全部";
    $scope.pages = [];



    $scope.initCategory = function () {
        $scope.selectPage(1);
    };



    //选择页数
    $scope.selectPage = function (page) {
        if ($scope.totalPage == 0 && (page < 1 || page > $scope.totalPage))
            return;
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
        $scope.currentPage = 1;
        console.log($scope.currentPage);
        if(status==0){
            $scope.currentStatus = "全部";
        }else if(status==1){
            $scope.currentStatus = "即将开课";
        }else if(status==2){
            $scope.currentStatus = "正在开课";
        }else if(status==3){
            $scope.currentStatus = "已完结";
        }
        $scope.getLessons();
    };


    //修改类别
    $scope.setSubject = function (subject) {
        $scope.currentPage = 1;
        console.log($scope.currentPage);
        if(subject==0){
            $scope.currentSubject = "全部";
        }else if(subject==1){
            $scope.currentSubject = "哲学";
        }else if(subject==2){
            $scope.currentSubject = "经济学";
        }else if(subject==3){
            $scope.currentSubject = "法学";
        }else if(subject==4){
            $scope.currentSubject = "教育学";
        }else if(subject==5){
            $scope.currentSubject = "文学";
        }else if(subject==6){
            $scope.currentSubject = "历史学";
        }else if(subject==7){
            $scope.currentSubject = "理学";
        }else if(subject==8){
            $scope.currentSubject = "工学";
        }else if(subject==9){
            $scope.currentSubject = "农学";
        }else if(subject==10){
            $scope.currentSubject = "医学";
        }else if(subject==11){
            $scope.currentSubject = "管理学";
        }else if(subject==12){
            $scope.currentSubject = "艺术学";
        }
        $scope.getLessons();
    };


    //发送请求
    $scope.getLessons = function () {
        let p = $scope.currentPage-1;
        $http({
            method: 'GET',
            url: "/lesson/pages?page="+p,
            params:{
                "status": $scope.currentStatus,
                "subject": $scope.currentSubject
            }
        }).then(function successCallback(response) {
            $scope.selectLesson = response.data.content;//获取返回的课程
            $scope.totalPage = response.data.totalPages;//获取最大页数
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


    //跳转到课程详情页面
    $scope.goDetail_2 = function (lesson) {
        $state.go('courseinfo', {
            "lesson": lesson
        });
    }

});



