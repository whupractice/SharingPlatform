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

    $scope.RootUrl=null;

    $scope.errorMessage=null;

    $scope.videoLinks = [];//视频链接和名字集合

    $scope.videoLink={};

    $scope.link="";//当前视频链接
    $scope.nowStar = 7;

    $scope.currentPage = 1;//当前集数
    $scope.totalPage = 1; // 总集数 （根据 总记录数、每页记录数 计算 ）
    $scope.pages = [];

    $scope.stuNum = 0;//这门课的学生数量
    $scope.hasSelect = false;



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
            $scope.hasLesson();//判断是否选择了这门课
            $scope.pages=[];
            for(var i = 1;i<=$scope.lesson.videoNum;i++){
                $scope.pages.push(i);
            }
            $scope.totalPage = $scope.lesson.videoNum;
            $scope.getTeachers();//获取老师信息
            $scope.getComments();//获取评论
            $scope.getTJlesson();//获取推荐课程
            $scope.getStuNum();//获取此课程学生数量
            $scope.showStars(7);
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
        var lessonId = window.localStorage.getItem('lessonId');
        $http({
            method: 'GET',
            url: "/sl/getStuNumByLessonId",
            params:{
                "lessonId" : lessonId
            }
        }).then(function successCallback(response) {
            $scope.stuNum = response.data.num;
        });
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


    //判断是否有评论
    $scope.judgeC=function(x){
      if(x.evaluation==null || x.evaluation.length==0)
      {
          return false;
      }
      else
      {
          return true;
      }
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
                $scope.getNickName(i);
            }
        })
    };


    $scope.getNickName = function(i){
        var sl = $scope.sl[i];
        $http({
            method: 'GET',
            url: '/student/getNickName',
            params: {
                "phone": sl.phone
            }
        }).then(function successCallback(response) {
            $scope.sl[i].nickName = response.data.nickName;
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


    //判断该学生是否有选择这门课程
    $scope.hasLesson = function(){
        var phone = window.localStorage.getItem('phone');
        var token = window.localStorage.getItem('token');
        $http({
            method: 'GET',
            url: '/sl/stuId',
            headers: {
                'Authorization': token
            },
            params: {
                "stuId": phone
            }
        }).then(function successCallback(response) {
            var lessonsInfo = response.data;
            var lessonId = window.localStorage.getItem('lessonId');
            for(var i = 0;i < lessonsInfo.length; i++){
                if(lessonId == lessonsInfo[i].lessonId) {
                    $scope.hasSelect = true;
                    $scope.changeVideo(1);
                }
            }
        });
    };

        //改变视频链接
        $scope.changeVideo = function (i) {
            // TODO 权限判断
            if($scope.hasSelect == true)
                $scope.link = $scope.lesson.videoLink+"_"+i;
        };

        //选择集数
        $scope._select = function (page) {
            if ($scope.totalPage == 0 && (page < 1 || page > $scope.totalPage))
                return;

            $scope.currentPage = page;
            $scope.changeVideo(page);
        };

        //上一集
        $scope._prev = function () {
            $scope._select($scope.currentPage-1);
        };

        //下一集
        $scope._next = function () {
            $scope._select($scope.currentPage+1);
        };





    // $scope.shareToSinaWB=function (event){
    //     var _title,_source,_sourceUrl,_pic,_showcount,_desc,_summary,_site,
    //         _width = 600,
    //         _height = 600,
    //         _top = (screen.height-_height)/2,
    //         _left = (screen.width-_width)/2,
    //         _url = 'www.baidu.com';
    //         // _pic = '';
    //
    //     var _shareUrl = 'http://v.t.sina.com.cn/share/share.php?&appkey=895033136';     //真实的appkey，必选参数
    //     _shareUrl += '&url='+ encodeURIComponent(_url||document.location);     //参数url设置分享的内容链接|默认当前页location，可选参数
    //     _shareUrl += '&title=' + encodeURIComponent(_title||document.title);    //参数title设置分享的标题|默认当前页标题，可选参数
    //     _shareUrl += '&source=' + encodeURIComponent(_source||'');
    //     _shareUrl += '&sourceUrl=' + encodeURIComponent(_sourceUrl||'');
    //     _shareUrl += '&content=' + 'utf-8';   //参数content设置页面编码gb2312|utf-8，可选参数
    //     _shareUrl += '&pic=' + encodeURIComponent(_pic||'');  //参数pic设置图片链接|默认为空，可选参数
    //     window.open(_shareUrl,'_blank','width='+_width+',height='+_height+',top='+_top+',left='+_left+',toolbar=no,menubar=no,scrollbars=no, resizable=1,location=no,status=0');
    // };
    // //分享到QQ空间
    // $scope.shareToQzone = function (event){
    //     var _title,_source,_sourceUrl,_pic,_showcount,_desc,_summary,_site,
    //         _width = 600,
    //         _height = 600,
    //         _top = (screen.height-_height)/2,
    //         _left = (screen.width-_width)/2,
    //         _url = 'http://localhost:8089/#!/couserinfo';
    //         // _pic = '';
    //
    //     var _shareUrl = 'http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?';
    //     _shareUrl += 'url=' + encodeURIComponent(_url||document.location);   //参数url设置分享的内容链接|默认当前页location
    //     _shareUrl += '&showcount=' + _showcount||0;      //参数showcount是否显示分享总数,显示：'1'，不显示：'0'，默认不显示
    //     _shareUrl += '&desc=' + encodeURIComponent(_desc||'分享的描述');    //参数desc设置分享的描述，可选参数
    //     _shareUrl += '&summary=' + encodeURIComponent(_summary||'分享摘要');    //参数summary设置分享摘要，可选参数
    //     _shareUrl += '&title=' + encodeURIComponent(_title||document.title);    //参数title设置分享标题，可选参数
    //     _shareUrl += '&site=' + encodeURIComponent(_site||'');   //参数site设置分享来源，可选参数
    //     _shareUrl += '&pics=' + encodeURIComponent(_pic||'');   //参数pics设置分享图片的路径，多张图片以＂|＂隔开，可选参数
    //     window.open(_shareUrl,'_blank','width='+_width+',height='+_height+',top='+_top+',left='+_left+',toolbar=no,menubar=no,scrollbars=no,resizable=1,location=no,status=0');
    // };





    $scope.showStars = function (n){
        var con_wid=document.getElementById("star_con").offsetWidth;
        var del_star=document.getElementById("del_star");
        console.log(con_wid);

        //透明星星移动的像素
        var del_move=(n*con_wid)/10;

        del_star.style.backgroundPosition=-del_move+"px 0px";
        del_star.style.left=del_move+"px";
    };

    });





