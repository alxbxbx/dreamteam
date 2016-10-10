
'use strict';

angular.module('DreamTeam').controller('RegisterController', ['$rootScope', '$http', '$scope', '$uibModal', '$log', 'Restangular', '_', 'authService',
    function ($rootScope, $http, $scope, $uibModal, $log, Restangular, _, authService) {
	$scope.message = "";
	
	$scope.passwordAgain = "";
	$scope.user = {
			username: "",
			password: "",
			name: "",
			lastName: "",
			email: "",
			isActive: false,
			approved: false
	}
	
	$scope.register = function(user){
		if(user.username == "" || user.password == "" || user.name == "" || user.lastName == "" || user.email == ""){
			$scope.message = "You must fill all fields!";
			$scope.openModal($scope.message);
		}else if(user.password != $scope.passwordAgain){
			$scope.message = "Passwords did no match!";
			$scope.openModal($scope.message);
		}else if(typeof user.email == "undefined"){
			$scope.message = "Please enter a valid email.";
			$scope.openModal($scope.message);
		}else{
			
			$.ajax({
				url: "http://localhost:8080/api/users/register",
				type: "POST",
				dataType: "json",
				contentType: "application/json",
				data: JSON.stringify(user),
				success: function(response){ 
					$scope.message = "You have successfully registered! Once our team approve your registration, you will be able to log in on your account.";
					$scope.openModal($scope.message);
				},
				
				error: function(response){ 
					if(response.status == 406){
						$scope.message = "User with that username already exists.";
						$scope.openModal($scope.message);
					}else if(response.status == 400){
						$scope.message = "You did not fill form correctly!";
						$scope.openModal($scope.message);
					}
				} 
			})
		}
	}
	
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
        	 window.location = "/#/login";
         };
         $scope.cancel = function () {
             $uibModalInstance.dismiss('cancel');
         };

     }];
	
	
	
}]);