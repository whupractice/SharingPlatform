var API_index = angular.module('myApp');

/**
 * @Author      : Theory
 * @Description : 主页面控制器
 * @type        : Controller
 */
API_index.controller("courseinfoCtrl", function ($scope, $http, $state,$stateParams,Data) {


    $scope.lesson = null;

    $scope.sl=null;//选课信息
    $scope.teacherNum = 0;

    $scope.scores=[];//课程打分

    $scope.now = 1;//当前位置

    $scope.Si=null;//Studentinformation
    $scope.teachers = null;

    $scope.currentScore = 4.3;
    $scope.f = 0.3;
    $scope.student=null;

    $scope.RootUrl=null;

    $scope.errorMessage=null;




    /**
      * @Author      : Theory
      * @Description : 初始化课程的信息
      */
    $scope.initCourseInfo = function(){
        $scope.lesson = $stateParams.lesson;//接收传来课程信息
       $scope.getTeachers();
       //$scope.getScores();
       $scope.getComments();

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
            let school = response.data;
            $state.go('schoolcourse',{
                "school": school
            })
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
        let lessonId = $scope.lesson.lessonId;//获取课程id
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
        $state.go('teacher',{
            "teacher" : teacher
        });
    };


    //课程打分
    $scope.getScores=function () {
        var score=Math.floor(4.3);
        $scope.f = 4.3 - score;
        for(let i=0;i<score;i++)
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
        let lessonId = $scope.lesson.lessonId;//获取课程id
        $http({
            method:'GET',
            url:'/sl/lessonId',
            params:{
                "lessonId" : lessonId
            }
        }).then(function successCallback(response) {
            $scope.sl=response.data;
            for(let i = 0;i<$scope.sl.length;i++){
                let sl = $scope.sl[i];
                $http({
                    method: 'GET',
                    url: '/student/info',
                    params: {
                        "phone": sl.phone
                    }
                }).then(function successCallback(response) {
                    ($scope.sl[i])["nickName"] = response.data.nickName;
                })
            }
        })
    };


 //参加课程
    $scope.joinCourse=function () {

        let lessonId = $scope.lesson.lessonId;//获取课程id
        $scope.student = Data.get();
        let phone = $scope.student.phone;

        if($scope.student.phone==0){  //未登录则跳转登录界面
            $state.go('login');
        }
        else if($scope.student.isManager == 1 || $scope.student.isLessonManager == 1){
            alert("管理员不能选课！");
            return;
        }
        else {
            $http({
                method: 'POST',
                url: '/sl',
                //如果swagger文档里参数是body类型，参数用data传递；若为query，参数用params
                data: {
                    "phone": phone,
                    "lessonId": lessonId
                }
            }).then(function successCallback(response) {
                if (response.status == 200) {
                    alert("success");
                    //TODO 跳转奥课程学习界面
                } else {
                    alert("插入失败");
                }
            })
        }
    };
    $scope.getRootUrl = function () {

        $http({
            method: 'GET',
            url: '/lesson/getRootUrl'
        }).then(function successCallback(response) {

            $scope.RootUrl=response.data.link+'carousel1.jpg/';
            // if(response.status==200){
            //     alert("GET请求成功");
            // }
            // else {
            //     alert("GET请求失败");
            // }
        })


    };

    //sharing



    // var _title,_source,_sourceUrl,_pic,_showcount,_desc,_summary,_site,
    //     _width = 600,
    //     _height = 600,
    //     _top = (screen.height-_height)/2,
    //     _left = (screen.width-_width)/2,
    //     _url = 'www.baidu.com',
    //     _pic = '';
    //分享到新浪微博
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





