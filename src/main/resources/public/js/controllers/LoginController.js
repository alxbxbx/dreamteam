
'use strict';

angular.module('DreamTeam').controller('LoginController', ['$rootScope', '$http', '$scope', '$uibModal', '$log', 'Restangular', '_',
    function ($rootScope, $http, $scope, $uibModal, $log, Restangular, _) {
	
	$scope.credentials = {};
	
	$scope.message = "";
	
	$scope.login = function() {
		var param = "Basic " + btoa("trusted-app:secret");
		var data = { "username": $scope.credentials.username, "password": $scope.credentials.password, "grant_type" : "password"};
		var config = { headers: { "Authorization": param }};
		/*
		$http.post('oauth/token', data, config).then(function(response){
			concole.log(response);
		});*/
		
		$.ajax({
			type: 'POST',
			url: 'http://localhost:8080/oauth/token',	
			headers: {"Authorization": param },
			data: data,
			success: function(response){ 
				var base64Url = response.access_token.split('.')[1];
				var base64 = base64Url.replace('-', '+').replace('_', '/');
				
				var token = "Bearer " + response.access_token;
				$http.defaults.headers.common.Authorization = token;
				localStorage.setItem('jwt_token', response.access_token);
				
				window.location = "/#/"
				
			},
			error: function(response){ 
				$scope.message = "Bad credentials!";
				$scope.openModal("Bad credentials!");
				console.log("Bad credentials!");
			} 
		})
		
		
	};
	
	
	$scope.openModal = function (message) {
        var modalInstance = $uibModal.open({
            templateUrl: '/../../images/message.html',
            controller: WarningCtrl,
            scope: $scope,
            resolve: {
                message: function () {
                    return message;
                }
            }
        });
        modalInstance.result.then(function (value) {
        }, function (value) {
        });
    };
    
    var WarningCtrl = ['$scope', '$uibModalInstance', 'message', 'Restangular', '$log',
     function ($scope, $uibModalInstance, message, Restangular, $log) {
         $scope.ok = function () {
        	 $uibModalInstance.dismiss('cancel');
         };
         $scope.cancel = function () {
             $uibModalInstance.dismiss('cancel');
         };

     }];
	
}]);