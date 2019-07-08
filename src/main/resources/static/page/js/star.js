var app = angular.module('myApp');

app.controller('starCtrl',function($scope){
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