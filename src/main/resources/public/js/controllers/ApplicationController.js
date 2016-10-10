
'use strict';

angular.module('DreamTeam').controller('ApplicationController', ['$rootScope', '$http', '$scope', '$uibModal', '$log', 'authService',
    function ($rootScope, $http, $scope, $uibModal, $log, authService) {
	
	$scope.isLoggedIn = authService.isLoggedIn;
	
	$scope.isAdmin = authService.isAdmin;
	
	$scope.logout = authService.logout;
	
}]);