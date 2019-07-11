var app = angular.module('myApp');



/**
 * @Author      : Theory
 * @Description : 学生控制器
 * @type        : Controller
 */
app.controller('studentCtrl', function ($scope, $http, $state) {   //Data是全局变量，保存当前用户


    $scope.currentStudent = null;

    $scope.skillLink = "";//技能图地址

    $scope.nowPage = 1;

    $scope.nowLesson = null;

    $scope.nowNews = null;//课程管理员发来的消息

    $scope.selectL = null;//当前选中的课程

    $scope.tjLesson = null;//推荐课程




    $scope.currentPage = 1;//当前页数
    $scope.totalPage = 1; // 总页数 （根据 总记录数、每页记录数 计算 ）
    $scope.pages=[];

    //选择页数
    $scope.selectPage = function (page) {
        if ($scope.totalPage == 0 && (page < 1 || page > $scope.totalPage))
            return;
        $scope.currentPage = page;
        $scope.getLessonByPhone();
    };

    //上一页
    $scope.prevPage = function () {
        $scope.selectPage($scope.currentPage-1);
    };

    //下一页
    $scope.nextPage = function () {
        $scope.selectPage($scope.currentPage+1);
    };



    //初始化学生信息
    $scope.initStudent = function () {
        var token = window.localStorage.getItem('token');
        var phone = window.localStorage.getItem('phone');
        $http({
            method: 'GET',
            url: '/student/info',
            headers: {
                'Authorization': token
            },
            params: {
                "phone": phone
            }
        }).then(function successCallback(response) {
            $scope.currentStudent = response.data;
            $scope.produceSkillImg();//生成技能图片
            $scope.getLessonByPhone();//获取当前课程
            $scope.getMessageByPhone();//获取消息


        });

    };


    //生成技能图片
    $scope.produceSkillImg = function(){
        var token = window.localStorage.getItem('token');
        var phone = window.localStorage.getItem('phone');
        $http({
            method: 'GET',
            url: '/student/skill',
            headers: {
                'Authorization': token
            },
            params: {
                "phone": phone
            }
        }).then(function successCallback(response) {
            $scope.skillLink = '../img/skill/'+phone+'.jpg';
        });
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
        var phone = window.localStorage.getItem('phone');
        var token = window.localStorage.getItem('token');
        var realName=$scope.currentStudent.realName;
        var nickName = $('#nickName').val();
        var pwd = $scope.currentStudent.pwd;
        var sex = $('#sex').find('option:selected').text();
        var email = $('#email').val();
        var birth = $('#birth').val();
        var introduction = $('#introduction').val();

        $http({
            method: 'PUT',
            url: '/student/updateStudent',
            headers: {
                'Authorization': token
            },
            data:{
                "phone": phone,
                "birth": birth,
                "email": email,
                "pwd":pwd,
                "realName":realName,
                "nickName": nickName,
                "introduction":introduction,
                "sex": sex
            }
        }).then(function successCallback(response) {
            var token = response.data.token;
            window.localStorage.setItem('token',token);
        })

    };


    //改变密码
    $scope.changePwd = function () {
        var primPwd = $('#primPwd').val();
        var newPwd = $('#newPwd').val();
        var confirmPwd = $('#confirmPwd').val();
        var phone = window.localStorage.getItem('phone');
        var token = window.localStorage.getItem('token');

        if(primPwd == newPwd){
            alert("前后密码一致，请重新输入！");
            return;
        }else if(newPwd != confirmPwd){
            alert("二次密码不一致！请重新确认密码！");
            return;
        }else{
            $http({
                method: 'GET',
                url: '/student/getUserFromToken',
                headers: {
                    'Authorization': token
                },
                params:{
                    'token': token
                }
            }).then(function successCallback(response) {
                var pwd = response.data.pwd;
                if(pwd==primPwd){
                    $http({
                        method: 'PUT',
                        url: '/student/updateStudent',
                        headers: {
                            'Authorization': token
                        },
                        data:{
                            "phone": phone,
                            "birth": $scope.currentStudent.birth,
                            "email": $scope.currentStudent.email,
                            "pwd":newPwd,
                            "realName":$scope.currentStudent.realName,
                            "nickName": $scope.currentStudent.nickName,
                            "introduction":$scope.currentStudent.introduction,
                            "sex": $scope.currentStudent.sex
                        }
                    }).then(function successCallback(response) {
                        var token = response.data.token;
                        window.localStorage.setItem('token',token);
                        alert("修改成功！");
                    })
                }
                else {
                    alert("用户名或者密码错误")
                }
            })
        }

    };


    // 根据学生电话号码查询所选课程
    $scope.getLessonByPhone = function () {
        var phone = window.localStorage.getItem('phone');
        var token = window.localStorage.getItem('token');
        $http({
            method: 'GET',
            url: '/sl/getLessonPagesNumByStuId',
            headers: {
                'Authorization': token
            },
            params:{
                "num": 5,
                "stuId": phone
           }
        }).then(function successCallback(response) {
            $scope.totalPage = response.data.numOfPages;
            $http({
                method: 'GET',
                url: '/sl/getLessonPagesByStuId',
                headers: {
                    'Authorization': token
                },
                params:{
                    "num": 5,
                    "stuId": phone,
                    "page" : $scope.currentPage-1
                }
            }).then(function successCallback(response) {
                $scope.nowLesson = response.data;//获取返回的课程
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
            })
        })
    };


    // $scope.getLessonByPhone = function(){
    //         var phone = window.localStorage.getItem('phone');
    //         var token = window.localStorage.getItem('token');
    //         $http({
    //             method: 'GET',
    //             url: '/sl/getLessonByStuId',
    //             headers: {
    //                 'Authorization': token
    //             },
    //             params:{
    //                 "stuId": phone
    //            }
    //         }).then(function successCallback(response) {
    //             $scope.nowLesson = response.data;
    //         })
    // };

    //进入课程
    $scope.enterLesson = function (x) {
        window.localStorage.setItem('lessonId',x.lessonId);
        $state.go('courseinfo');
    };




    //删除所选课程
    $scope.deleteLesson_ = function () {
        var phone = window.localStorage.getItem('phone');
        var token = window.localStorage.getItem('token');
        $http({
            method: 'DELETE',
            url: '/sl/stuId/lessonId',
            headers: {
                'Authorization': token
            },
            params:{
                "stuId": phone,
                "lessonId": $scope.selectL.lessonId
            }
        }).then(function successCallback(response) {
            if(response.status==200){
                $('#devareL_modal').modal('hide');
                $scope.getLessonByPhone();
            }
            else {
                alert("删除失败！");
            }
        })
    };



    //绑定当前选中课程
    $scope.bindL = function (x) {
        $scope.selectL = x;
    };



    //给课程打分&评论

    $scope.s_comment = function () {
        var token = window.localStorage.getItem('token');
        var evaTime	= $scope.selectL.evaTime;
        var evaluation= $('#evaluation').val();
        var lessonId = $scope.selectL.lessonId;
        var lessonProcess =$scope.selectL.lessonProcess;
        var phone = window.localStorage.getItem('phone');
        var token = window.localStorage.getItem('token');
        var praiseNum = $scope.selectL.praiseNum;
        var star = $('#rating').text();
        $http({
            method: 'PUT',
            url: '/sl',
            headers: {
                'Authorization': token
            },
            data:{
                "evaTime": evaTime,
                "evaluation": evaluation,
                "lessonId": lessonId,
                "lessonProcess": lessonProcess,
                "phone": phone,
                "praiseNum": praiseNum,
                "star": star
            }
        }).then(function successCallback(response) {
            if(response.status==200){
                $('#scoreModal').modal('hide');
                $scope.getLessonByPhone();
            }
            else {
                alert("评论失败！");
            }
        })
    };



    //查看课程消息
    $scope.getMessageByPhone = function () {
        var phone = window.localStorage.getItem('phone');
        var token = window.localStorage.getItem('token');
        $http({
            method: 'GET',
            url: '/message/phone',
            headers: {
                'Authorization': token
            },
            params:{
                "phone": phone
            }
        }).then(function successCallback(response) {
            $scope.nowNews = response.data;
        })
    };


    //上传头像








    /**
     * @Author      : Theory
     * @Description : 跳转到课程详情页
     * @Param       : 被点击的课程
     */
    $scope.goDetail = function (lesson) {
        window.localStorage.setItem('lessonId',lesson.lessonId);
        $state.go('courseinfo');
    };











    $scope.max = 5;
    $scope.ratingVal = 2;
    $scope.readonly = false;
    $scope.onHover = function(val){
        $scope.hoverVal = val;
    };
    $scope.onLeave = function(){
        $scope.hoverVal = null;
    };
    $scope.onChange = function(val){
        $scope.ratingVal = val;
    }
});



app.directive('star', function () {
    return {
        template: '<ul class="rating" ng-mouseleave="leave()">' +
            '<li ng-repeat="star in stars" ng-class="star" ng-click="click($index + 1)" ng-mouseover="over($index + 1)">' +
            '\u2605' +
            '</li>' +
            '</ul>',
        scope: {
            ratingValue: '=',
            max: '=',
            readonly: '@',
            onHover: '=',
            onLeave: '='
        },
        controller: function($scope){
            $scope.ratingValue = $scope.ratingValue || 0;
            $scope.max = $scope.max || 5;
            $scope.click = function(val){
                if ($scope.readonly && $scope.readonly === 'true') {
                    return;
                }
                $scope.ratingValue = val;
            };
            $scope.over = function(val){
                $scope.onHover(val);
            };
            $scope.leave = function(){
                $scope.onLeave();
            }
        },
        link: function (scope, elem, attrs) {
            elem.css("text-align", "center");
            var updateStars = function () {
                scope.stars = [];
                for (var i = 0; i < scope.max; i++) {
                    scope.stars.push({
                        filled: i < scope.ratingValue
                    });
                }
            };
            updateStars();

            scope.$watch('ratingValue', function (oldVal, newVal) {
                if (oldVal) {
                    updateStars();
                }
            });
            scope.$watch('max', function (oldVal, newVal) {
                if (newVal) {
                    updateStars();
                }
            });
        }
    };
});


app.directive('myStars', function () {
    return {
        require : '?ngModel', // ?ngModel
        restrict : 'E',
        replace : true,
        templateUrl : 'ui/templateUrl/myStars.html',
        scope: {ngModel : '='},
        link: function ($scope, element, attrs, ngModel) {
            $scope.myStars = [1,2,3,4,5];
            $scope.clickCnt = 1;
            $scope.$watch('ngModel', function(newValue) {
                var dataList = newValue;
                console.log(dataList);
                if(!dataList) return;
                $scope.myStar = dataList;
                $scope.clickCnt = dataList;
            });
            $scope.stars = function (myStar) {
                $scope.clickCnt = myStar;
                ngModel.$setViewValue(myStar);
            };

            $scope.mouseoverStar = function (myStar) {
                $scope.hoverCnt = myStar;
            };
            $scope.mouseleaveStar = function (myStar) {
                $scope.hoverCnt = 1;
            }
        }
    }
});