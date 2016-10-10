
'use strict';

angular.module('DreamTeam').controller('HomeController', ['$rootScope', '$http', '$scope', '$uibModal', '$log', 'Restangular', '_', 'authService',
    function ($rootScope, $http, $scope, $uibModal, $log, Restangular, _, authService) {
	
	if(!authService.isLoggedIn()){
		window.location = "/#/login";
	}
	
	$scope.dreamTeams = [];
	//$.get("http://localhost:8080/api/export/dreamteam/17");
	
	var loadTeams = function(){
		 Restangular.all("dreamteam").getList().then(function (entries) {
	            $scope.dreamTeams = _.orderBy(entries, 'successIndex', 'desc');
	        });
	};
	
	loadTeams();
}]);