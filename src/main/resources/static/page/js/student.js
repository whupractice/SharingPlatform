var app = angular.module('myApp');

/**
 * @Author      : Theory
 * @Description : 学生控制器
 * @type        : Controller
 */
app.controller('studentCtrl', function ($scope, $http, $state,Data) {   //Data是全局变量，保存当前用户


    $scope.currentStudent = null;

    $scope.nowPage = 1;


    //初始化学生信息
    $scope.initstudent = function () {
        $scope.currentStudent = Data.get();//获取当前学生信息
    };


    //跳转页面
    $scope.jump_ = function (page) {
        $scope.nowPage = page;
    };


    //第一面
    $scope._page_1_ = function () {
        return $scope.nowPage == 1;
    };
    //第二面
    $scope._page_2_ = function () {
        return $scope.nowPage == 2;
    };
    //第三面
    $scope._page_3_ = function () {
        return $scope.nowPage == 3;
    };
    //第四面
    $scope._page_4_ = function () {
        return $scope.nowPage == 4;
    };

    //更新学生信息
    $scope.updateStuInfo = function () {
        let phoneNumber = $scope.currentStudent.phone;
        let  realName=$scope.currentStudent.realName;
        let nickName = $('#nickName').val();
        let pwd = $scope.currentStudent.pwd;
        let sex = $('#sex').find('option:selected').text();
        let email = $('#email').val();
        let birth = $('#birth').val();
        let introduction = $('#introduction').val();

        $http({
            method: 'PUT',
            url: '/student',
            data:{
                "phone": phoneNumber,
                "birth": birth,
                "email": email,
              "pwd":pwd,
                "realName":realName,
                "nickName": nickName,
"introduction":introduction,
                "sex": sex
            }
        }).then(function successCallback(response) {
            if(response.status == 200){
                alert("修改成功！");
            }else{
                alert("修改失败!");
            }
        })

    };




});
