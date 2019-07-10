var API_index = angular.module('myApp');

/**
 * @Author      : Theory
 * @Description : 主页面控制器
 * @type        : Controller
 */
API_index.controller("courseinfoCtrl", function ($scope, $http, $state) {


    $scope.lesson = null;
    $scope.tj_lesson = null;//推荐课程

    $scope.sl=null;//选课信息
    $scope.teacherNum = 0;

    $scope.scores=[];//课程打分

    $scope.now = 1;//当前位置

    $scope.Si=null;//Studentinformation
    $scope.teachers = null;

    $scope.currentScore = 4.3;
    $scope.f = 0.3;
    $scope.nickName="";

    $scope.RootUrl=null;

    $scope.errorMessage=null;




    //获取推荐课程
    $scope.getTJlesson = function(){
        var phone = window.localStorage.getItem('phone');
        var token = window.localStorage.getItem('token');
        if(phone==null){
            return;
        }
        $http({
            method: 'GET',
            url: '/lesson/tj',
            headers: {
                'Authorization': token
            },
            params:{
                "phone": phone
            }
        }).then(function successCallback(response) {
            $scope.tj_lesson = response.data;
        })
    };



    /**
      * @Author      : Theory
      * @Description : 初始化课程的信息
      */
    $scope.initCourseInfo = function(){
        var lessonId = window.localStorage.getItem('lessonId');
        $http({
            method: 'GET',
            url: "/lesson/id",
            params:{
                "lessonId" : lessonId
            }
        }).then(function successCallback(response) {
            $scope.lesson = response.data;
            $scope.getTeachers();//获取老师信息
            $scope.getComments();//获取评论
            $scope.getTJlesson();//获取推荐课程
        });

    };

    //去往推荐课程
    $scope.goTjLesson = function(x){
        window.localStorage.setItem('lessonId',x.lessonId);
        $state.go('courseinfo');
    };



    /**
      * @Author      : Theory
      * @Description : 获取此门课程的学习人数
      */
    $scope.getStuNum = function () {
        //TODO
    };


    //去往学校界面
    $scope.goSchool2 = function () {
        $http({
            method: 'GET',
            url: "/school/schoolName",
            params:{
                "schoolName" : $scope.lesson.schoolName
            }
        }).then(function successCallback(response) {
            var school = response.data;
            window.localStorage.setItem('schoolId',school.schoolId);
            $state.go('schoolcourse');
        });
    };

    //跳转
    $scope.jump = function(page){
        return $scope.now = page;
    };
    //显示课程详情内容
    $scope.CoursePage = function(){
      return $scope.now == 1;
    };
    //显示评论区
    $scope.CommentPage = function(){
        return $scope.now == 2;
    };


    $scope.getTeachers = function () {
        var lessonId = $scope.lesson.lessonId;//获取课程id
        $http({
            method: 'GET',
            url: '/tl/getTeacherByLessonId',
            params:{
                "lessonId" : lessonId
            }
        }).then(function successCallback(response) {
            $scope.teachers=response.data;
            $scope.teacherNum = $scope.teachers.length;
        });
    };

    //跳转到教师页面
    $scope.jumpT = function (teacher) {
        window.localStorage.setItem('teacherId',teacher.teacherId);
        $state.go('teacher');
    };


    //课程打分
    $scope.getScores=function () {
        var score=Math.floor(4.3);
        $scope.f = 4.3 - score;
        for(var i=0;i<score;i++)
        {
            $scope.scores.push(1);
        }
            };
    $scope.judge=function () {
        return $scope.f>0.5;
    };
    $scope.judge2=function () {
        return $scope.f<=0.5;
    };

    //获取评论信息
    $scope.getComments=function () {
        var lessonId = $scope.lesson.lessonId;//获取课程id
        $http({
            method:'GET',
            url:'/sl/lessonId',
            params:{
                "lessonId" : lessonId
            }
        }).then(function successCallback(response) {
            $scope.sl=response.data;
            for(var i = 0;i<$scope.sl.length;i++){
                var sl = $scope.sl[i];
                $http({
                    method: 'GET',
                    url: '/student/getNickName',
                    params: {
                        "phone": sl.phone
                    }
                }).then(function successCallback(response) {
                        $scope.nickName = response.data.nickName;
                })
            }
        })
    };


    //参加课程
    $scope.joinCourse=function () {

        var lessonId = $scope.lesson.lessonId;//获取课程id
        var phone = window.localStorage.getItem('phone');
        var token = window.localStorage.getItem('token');
        if(phone==null){
            $state.go('login');
        }
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
            var student = response.data;
            if(student.isManager == 1 || student.isLessonManager == 1){
                alert("管理员不能选课！");
                return;
            }
            else {
                $http({
                    method: 'POST',
                    url: '/sl',
                    headers: {
                        'Authorization': token
                    },
                    data: {
                        "phone": phone,
                        "lessonId": lessonId
                    }
                }).then(function successCallback(response) {
                    if (response.status == 200) {
                        alert("success");
                        //TODO 跳转到课程学习界面
                    } else {
                        alert("插入失败");
                    }
                })
            }
        });


    };


    $scope.shareToSinaWB=function (event){
        var _title,_source,_sourceUrl,_pic,_showcount,_desc,_summary,_site,
            _width = 600,
            _height = 600,
            _top = (screen.height-_height)/2,
            _left = (screen.width-_width)/2,
            _url = 'www.baidu.com',
            _pic = '';

        var _shareUrl = 'http://v.t.sina.com.cn/share/share.php?&appkey=895033136';     //真实的appkey，必选参数
        _shareUrl += '&url='+ encodeURIComponent(_url||document.location);     //参数url设置分享的内容链接|默认当前页location，可选参数
        _shareUrl += '&title=' + encodeURIComponent(_title||document.title);    //参数title设置分享的标题|默认当前页标题，可选参数
        _shareUrl += '&source=' + encodeURIComponent(_source||'');
        _shareUrl += '&sourceUrl=' + encodeURIComponent(_sourceUrl||'');
        _shareUrl += '&content=' + 'utf-8';   //参数content设置页面编码gb2312|utf-8，可选参数
        _shareUrl += '&pic=' + encodeURIComponent(_pic||'');  //参数pic设置图片链接|默认为空，可选参数
        window.open(_shareUrl,'_blank','width='+_width+',height='+_height+',top='+_top+',left='+_left+',toolbar=no,menubar=no,scrollbars=no, resizable=1,location=no,status=0');
    };
    //分享到QQ空间
    $scope.shareToQzone = function (event){
        var _title,_source,_sourceUrl,_pic,_showcount,_desc,_summary,_site,
            _width = 600,
            _height = 600,
            _top = (screen.height-_height)/2,
            _left = (screen.width-_width)/2,
            _url = 'http://localhost:8089/#!/couserinfo',
            _pic = '';

        var _shareUrl = 'http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?';
        _shareUrl += 'url=' + encodeURIComponent(_url||document.location);   //参数url设置分享的内容链接|默认当前页location
        _shareUrl += '&showcount=' + _showcount||0;      //参数showcount是否显示分享总数,显示：'1'，不显示：'0'，默认不显示
        _shareUrl += '&desc=' + encodeURIComponent(_desc||'分享的描述');    //参数desc设置分享的描述，可选参数
        _shareUrl += '&summary=' + encodeURIComponent(_summary||'分享摘要');    //参数summary设置分享摘要，可选参数
        _shareUrl += '&title=' + encodeURIComponent(_title||document.title);    //参数title设置分享标题，可选参数
        _shareUrl += '&site=' + encodeURIComponent(_site||'');   //参数site设置分享来源，可选参数
        _shareUrl += '&pics=' + encodeURIComponent(_pic||'');   //参数pics设置分享图片的路径，多张图片以＂|＂隔开，可选参数
        window.open(_shareUrl,'_blank','width='+_width+',height='+_height+',top='+_top+',left='+_left+',toolbar=no,menubar=no,scrollbars=no,resizable=1,location=no,status=0');
    };

    });





