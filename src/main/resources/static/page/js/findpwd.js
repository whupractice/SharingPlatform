var app = angular.module('myApp');


/**
 * @Author      : Theory
 * @Description : 找回密码控制器
 * @type        : Controller
 */
app.controller('findpwdCtrl', function ($scope, $http, $state,Data) {

    $scope.currentUser = null;

    $scope.nowPage = 1;



    //
    $scope.verify_resetPwd = function(page){

        let user = $('#p_number').val();
        let v_code = $('#verify_code_').val();

        $http({
            method: 'GET',
            url: '/student/info',
            //如果swagger文档里参数是body类型，参数用data传递；若为query，参数用params
            params:{
                "phone": user
            }
        }).then(function successCallback(response) {
            if(response.data.length == 0){
                alert("输入账号不存在！")
            }
            else {
                $scope.currentUser = response.data;

                $scope.nowPage = page;


            }
        })
    };


    $scope.code_page_1 = function () {
        return $scope.nowPage == 1;
    };


    $scope.code_page_2 = function () {
        return $scope.nowPage == 2;
    };
    //重置密码



})