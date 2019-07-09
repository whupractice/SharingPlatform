var app = angular.module('myApp');

/**
 * @Author      : Theory
 * @Description : 系统管理员页面控制器
 * @type        : Controller
 */
app.controller('lessonManagerCtrl', function ($scope, $http) {

    $scope.currentManager = null;//当前课程管理员

    $scope.currentLessons = null;//当前所有课程

    $scope.nowPage = 1;

    $scope.totalP_ = 1;//课程总页数
    $scope.currentP_ = 1;//课程当前页数
    $scope.Ps_ = [];

    $scope.currentL = null;//当前选中课程

    $scope.teachers = null;//此学校的所有老师
    $scope.currentT = null;//当前选中的老师

    $scope.academys = null;//此学校的所有学院
    $scope.academyLesson = null;//此学院的所有课程

    $scope.imgForm = {
        img: null,
        fileName:""
    };


    //初始化管理员信息
    $scope.initLessonManager = function () {
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
            $scope.currentManager = response.data;
            $scope.selectP_(1);
            $scope.getTeachers();//获取老师信息
            $scope.getAcademys();//获取学院信息
        });

    };


    //跳转页面
    $scope.jump = function (page) {
        $scope.nowPage = page;
    };


    //第一面
    $scope._page_1 = function () {
        return $scope.nowPage == 1;
    };
    //第二面
    $scope._page_2 = function () {
        return $scope.nowPage == 2;
    };
    //第三面
    $scope._page_3 = function () {
        return $scope.nowPage == 3;
    };



    //更新课程管理员的信息
    $scope.updateLeMan = function () {
        var token = window.localStorage.getItem('token');
        var phone = window.localStorage.getItem('phone');
        var realName = $('#realName').val();
        var nickName = $('#nickName').val();
        var pwd = $('#pwd').val();
        var sex = $('#sex').find('option:selected').text();
        var email = $('#email').val();
        var birth = $('#birth').val();
        var introduction = $('#introduction').val();

        $http({
            method: 'PUT',
            url: '/student/updateLessonManager',
            headers: {
                'Authorization': token
            },
            data:{
                "phone": phone,
                "birth": birth,
                "email": email,
                "introduction": introduction,
                "schoolName": $scope.currentManager.schoolName,
                "academyName": $scope.currentManager.academyName,
                "isLessonManager": 1,
                "isManager": 0,
                "nickName": nickName,
                "pwd": pwd,
                "realName": realName,
                "sex": sex
            }
        }).then(function successCallback(response) {
            if(response.status == 200){
                alert("修改成功！");
                var token = response.data.token;
                window.localStorage.setItem('token',token);
                $scope.initLessonManager();
            }else{
                alert("修改失败!");
            }
        })

    };




    //分页获取当前管理员所在学院的课程
    $scope.getLessons = function () {
        var token = window.localStorage.getItem('token');
        var p = $scope.currentP_-1;
        $http({
            method: 'GET',
            url: '/lesson/pagesBySchoolName?page='+p,
            headers: {
                'Authorization': token
            },
            params: {
                "schoolName": $scope.currentManager.schoolName
            }
        }).then(function successCallback(response) {
            $scope.currentLessons = response.data.content;//获取返回的课程
            $scope.totalP_ = response.data.totalPages;//获取最大页数
            $scope.Ps_ = [];
            if($scope.totalP_>5) {
                var start = ($scope.currentP_>=3) ? $scope.currentP_-2 : 1;
                var end = ($scope.currentP_<=$scope.totalP_-2) ? start+4 : $scope.totalP_;
                for(var i = start;i<=end;i++)
                    $scope.Ps_.push(i);
            }else{
                for(var i = 1;i<=$scope.totalP_;i++)
                    $scope.Ps_.push(i);
            }
        })
    };

    //选择页号
    $scope.selectP_ = function (page) {
        if ($scope.totalP_ == 0 && (page < 1 || page > $scope.totalP_))
            return;
        $scope.currentP_ = page;
        $scope.getLessons();
    };

    //上一页
    $scope.pre_ = function () {
        $scope.selectP_($scope.currentP_-1);
    };

    //下一页
    $scope.next_ = function () {
        $scope.selectP_($scope.currentP_+1);
    };


    //绑定课程
    $scope.bindLesson = function (x) {
        $scope.currentL = x;
    };




    //添加课程
    $scope.addLesson = function () {
        var token = window.localStorage.getItem('token');
        var lessonName = $('#addLessonName').val();
        var subject = $('#addSubject').find('option:selected').text();
        var startTime = $('#addStartTime').val();
        var endTime = $('#addEndTime').val();
        var credit =  $('#addCredit').find('option:selected').text();
        var introduction = $('#addIntroduction').val();
        var schoolName = $scope.currentManager.schoolName;
        var academyName = $scope.currentManager.academyName;
        var recommended_level = $('#addRecommend').find('option:selected').val();

        var status = "";

        var nowTime = $scope.getNowFormatDate();
        if($scope.compareDate(startTime,endTime)==false){
            alert("开始时间不能大于结束时间！");
        }else if($scope.compareDate(startTime,nowTime)==false){
            status = "即将开课";
        }else if($scope.compareDate(nowTime,endTime)==false){
            status = "已完结";
        }else{
            status="正在开课";
        }

        $http({
            method: 'POST',
            url: '/lesson',
            headers: {
                'Authorization': token
            },
            data:{
                "academyName": academyName,
                "credit": credit,
                "endTime": endTime,
                "lessonIntro": introduction,
                "lessonName": lessonName,
                "recommendedLevel": recommended_level,
                "schoolName": schoolName,
                "startTime": startTime,
                "status": status,
                "subject": subject
            }
        }).then(function successCallback(response) {
            if(response.status == 200){
                alert("添加成功！");
                $scope.getLessons();
            }else{
                alert("添加失败!");
            }
        })
    };




    //比较时间
    $scope.compareDate = function(startDate, endDate)
    { 
        var arrStart = startDate.split("-"); 
        var startTime = new Date(arrStart[0], arrStart[1], arrStart[2]);
        var startTimes = startTime.getTime(); 
        var arrEnd = endDate.split("-"); 
        var endTime = new Date(arrEnd[0], arrEnd[1], arrEnd[2]); 
        var endTimes = endTime.getTime();
        if (endTimes <startTimes){ 
            return false;
        }
        return true;
    };

    //获取当前时间
    $scope.getNowFormatDate=function () {       
        var date = new Date();       
        var seperator1 = "-";       
        var year = date.getFullYear();       
        var month = date.getMonth() + 1;       
        var strDate = date.getDate();       
        if (month >= 1 && month <= 9) {           
            month = "0" + month;       
        }       
        if (strDate >= 0 && strDate <= 9) {           
            strDate = "0" + strDate;       
        }       
        var currentdate = year + seperator1 + month + seperator1 + strDate;       
        return currentdate;   
    };

    //删除当前课程
    $scope.deleteLesson =function () {
        var token = window.localStorage.getItem('token');
        var lessonId = $scope.currentL.lessonId;//课程id
        $http({
            method: 'DELETE',
            url: '/lesson',
            headers: {
                'Authorization': token
            },
            params: {
                "lessonId": lessonId
            }
        }).then(function successCallback(response) {
            if(response.status == 200){
                alert("删除成功！");
                $('#deleteLModal').modal('hide');
                $scope.getLessons();
            }
        })
    };


    //向选了这门课的学生发消息
    $scope.send = function(){
        var token = window.localStorage.getItem('token');
        var message = $('#message').val();
        var lessonId = $scope.currentL.lessonId.toString();
        $http({
            method: 'GET',
            url: '/sl/getStudentByLessonId',
            headers: {
                'Authorization': token
            },
            params: {
                "lessonId": lessonId
            }
        }).then(function successCallback(response) {
            var stus = response.data;//获取选了这门课的所有学生
            for(var i = 0;i<stus.length;i++){
                var phone = stus[i].phone;
                var time = $scope.getNowFormatDate();
                $http({
                    method: 'POST',
                    url: '/message',
                    headers: {
                        'Authorization': token
                    },
                    data: {
                        "lessonId": $scope.currentL.lessonId,
                        "phone": phone,
                        "message": message,
                        "time": time
                    }
                }).then(function successCallback(response) {
                })
            }
            alert("发送成功！");

        })
    };



    //获取当前学校的所有教师
    $scope.getTeachers = function () {
        var token = window.localStorage.getItem('token');
        $http({
            method: 'GET',
            url: '/teacher/schoolName',
            headers: {
                'Authorization': token
            },
            params: {
                "schoolName": $scope.currentManager.schoolName
            }
        }).then(function successCallback(response) {
            $scope.teachers = response.data;
        })
    };

    //获取此学校的所有学院
    $scope.getAcademys = function () {
        var token = window.localStorage.getItem('token');
        $http({
            method: 'GET',
            url: '/academy/schoolName',
            headers: {
                'Authorization': token
            },
            params: {
                "schoolName": $scope.currentManager.schoolName
            }
        }).then(function successCallback(response) {
            $scope.academys = response.data;
        })
    };


    //增添老师
    $scope.addTeacher = function () {
        var token = window.localStorage.getItem('token');
        var teacherName = $('#addTName').val();
        var job = $('#addTJob').val();
        var academyName = $('#addTAcademy').find('option:selected').text();
        var schoolName = $scope.currentManager.schoolName;
        var teacherIntro = $('#addTIntro').val();
        var lessonId = $('#addTLesson').find('option:selected').val();

        $http({
            method: 'POST',
            url: '/teacher',
            headers: {
                'Authorization': token
            },
            data: {
                "teacherName": teacherName,
                "job":job,
                "academyName": academyName,
                "schoolName": schoolName,
                "teacherIntro": teacherIntro
            }
        }).then(function successCallback(response) {
            if(response.status==200) {
                var teacherId = response.data;
                $http({
                    method: 'POST',
                    url: '/tl',
                    headers: {
                        'Authorization': token
                    },
                    data: {
                        "lessonId": lessonId,
                        "teacherId": teacherId
                    }
                }).then(function successCallback(response) {
                    if(response.status == 200){
                        alert("添加成功!");
                        $('#addTModal').modal('hide');
                        $scope.getTeachers();
                    }else{
                        alert("添加失败！");
                    }
                })
            }else{
                alert("添加失败！");
            }
        })
    };


    //获取当前学校和学院下的课程
    $scope.getAcademyLesson = function () {
        var token = window.localStorage.getItem('token');
        var academyName = $('#addTAcademy').find('option:selected').text();
        var schoolName = $scope.currentManager.schoolName;
        $http({
            method: 'GET',
            url: '/lesson/schoolAndAcademy',
            headers: {
                'Authorization': token
            },
            params: {
                "schoolName": schoolName,
                "academyName": academyName
            }
        }).then(function successCallback(response) {
            $scope.academyLesson = response.data;
        })
    };


    //删除老师
    $scope.deleteT = function (x) {
        var token = window.localStorage.getItem('token');
        $http({
            method: 'DELETE',
            url: '/teacher',
            headers: {
                'Authorization': token
            },
            params: {
                "teacherId": x.teacherId
            }
        }).then(function successCallback(response) {
            if(response.status==200){
                $http({
                    method: 'DELETE',
                    url: '/tl/teacherId',
                    headers: {
                        'Authorization': token
                    },
                    params: {
                        "teacherId": x.teacherId
                    }
                }).then(function successCallback(response) {
                    if(response.status==200){
                        alert("删除成功！");
                        $scope.getTeachers();
                    }else{
                        alert("删除失败!");
                    }
                })
            }else{
                alert("删除失败!");
            }
        })
    };

    $scope.uploadImg = function() {
        $("#uploadImgForm").ajaxForm(function (data) {
            alert("上传成功！");
            $('#uploadModal').modal('hide');
        });
    };

    $scope.uploadTImg = function() {
        $("#teacherImgForm").ajaxForm(function (data) {
            alert("上传成功！");
            $('#imgModal').modal('hide');
        });
    };

    $scope.uploadVideo = function() {
        $("#uploadVideoForm").ajaxForm(function (data) {
            alert("上传成功！");
            $('#uploadModal').modal('hide');
        });
    };


    //绑定当前选中的老师
    $scope.bindT = function (x) {
        $scope.currentT = x;
    };




});



