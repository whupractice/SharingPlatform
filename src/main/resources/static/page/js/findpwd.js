var app = angular.module('myApp');


/**
 * @Author      : Theory
 * @Description : 找回密码控制器
 * @type        : Controller
 */
app.controller('findpwdCtrl', function ($scope, $http, $state,Data) {

    $scope.currentUser = null;

    $scope.nowPage = 1;


    //验证手机号
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
    $scope.reset_pwd = function(){

        let pwd = $('#new_pwd_1').val();
        let phoneNumber = $scope.currentUser.phone;
        let nickName = $scope.currentUser.nickName;
        let sex = $scope.currentUser.sex;
        let email = $scope.currentUser.email;
        let birth = $scope.currentUser.birth;
        let introduction = $scope.currentUser.introduction;

        if ( pwd == $('#new_pwd_2').val()){
            $http({
                method: 'PUT',
                url: '/student',
                //如果swagger文档里参数是body类型，参数用data传递；若为query，参数用params
                data: {
                    "birth": birth,
                    "email": email,
                    "introduction": introduction,
                    "nickName": nickName,
                    "phone": phoneNumber,
                    "pwd": pwd,
                    "sex": sex
                }
            }).then(function successCallback(response) {
                if (response.data == true) {
                    alert('修改密码成功')

                } else {
                    alert("修改密码失败")
                }
                $state.go('login');

            })
        }
        else {
            alert("两次密码输入不一致！")
        }
    };


})