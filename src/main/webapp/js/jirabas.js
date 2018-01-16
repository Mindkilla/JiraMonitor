/**
 * Created by smiana on 15.01.2018.
 */
var monitor = angular.module('monitor', []);
var other = angular.module('other', []);
var issueCurrMonth;
monitor.controller('curClosed', function ($scope, $http, $sce) {
    $http.get("/monitor/currentClosed")
        .then(function (result) {
            console.log(result);
            $scope.currentClosed = $sce.trustAsHtml(result.data);
        }, function (error) {
            console.error(error);
        });
});

monitor.controller('monthLider', function ($scope, $http) {
    $http.get("/monitor/monthLider")
        .then(function (result) {
            console.log(result);
            $scope.monthLider = result.data;
        }, function (error) {
            console.error(error);
        });
});

monitor.controller('todayIssues', function ($scope, $http, $sce) {
    $http.get("/monitor/todayIssues")
        .then(function (result) {
            console.log(result);
            $scope.todayIssues = $sce.trustAsHtml(result.data);
        }, function (error) {
            console.error(error);
        });
});

monitor.controller('currentActive', function ($scope, $http, $sce) {
    $http.get("/monitor/currentActive")
        .then(function (result) {
            console.log(result);
            $scope.currentActive = $sce.trustAsHtml(result.data);
        }, function (error) {
            console.error(error);
        });
});

monitor.controller('issueCurrMonth', function ($scope, $http, $sce) {
    if (issueCurrMonth != null) {
        $scope.issueCurrMonth = $sce.trustAsHtml(issueCurrMonth);
    }
    else {
        $http.get("/monitor/issueCurrMonth")
            .then(function (result) {
                console.log(result);
                issueCurrMonth= result.data;
                $scope.issueCurrMonth = $sce.trustAsHtml(result.data);
            }, function (error) {
                console.error(error);
            });
    }
});

monitor.controller('issueOpenedNow', function ($scope, $http, $sce) {
    $http.get("/monitor/issueOpenedNow")
        .then(function (result) {
            console.log(result);
            $scope.issueOpenedNow = $sce.trustAsHtml(result.data);
        }, function (error) {
            console.error(error);
        });
});

monitor.controller('issueClosedMonth', function ($scope, $http, $sce) {
    $http.get("/monitor/issueClosedMonth")
        .then(function (result) {
            console.log(result);
            $scope.issueClosedMonth = $sce.trustAsHtml(result.data);
        }, function (error) {
            console.error(error);
        });
});

//Other actions
other.controller('vacation-controller', function ($scope, $http, $sce) {
    $http.get("/other/vacation")
        .then(function (result) {
            console.log(result);
            $scope.vacation = $sce.trustAsHtml(result.data);
        }, function (error) {
            console.error(error);
        });
});

other.controller('inactivity-controller', function ($scope, $http, $sce) {
    $http.get("/other/inactivity")
        .then(function (result) {
            console.log(result);
            $scope.inactivity = $sce.trustAsHtml(result.data);
        }, function (error) {
            console.error(error);
        });
});