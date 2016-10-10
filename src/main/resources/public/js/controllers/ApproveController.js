'use strict';

angular.module('DreamTeam').controller('ApproveController', ['$rootScope', '$http', '$scope', '$uibModal', '$log', 'Restangular', '_', 'authService',
    function ($rootScope, $http, $scope, $uibModal, $log, Restangular, _, authService) {
	
	$scope.users = [];
	$scope.message = "";
	
	var loadUsers = function(){
		 Restangular.all("/pendingusers").getList().then(function (entries) {
			 $scope.users = entries;
	     });
	};
	$scope.openModal = function (user) {
        var modalInstance = $uibModal.open({
            templateUrl: '/../../images/approveModal.html',
            controller: ApproveCtrl,
            scope: $scope,
            resolve: {
            	user: function () {
                    return user;
                }
            }
        });
        modalInstance.result.then(function (value) {
        }, function (value) {
        });
    };
    
    var ApproveCtrl = ['$rootScope', '$scope', '$uibModalInstance', 'user', 'Restangular', '$log',
     function ($rootScope, $scope, $uibModalInstance, user,
			Restangular, $log) {
		$scope.ok = function() {
			user.approved = true;
			Restangular.all('users').customPUT(user).then(function (data) {
           		$scope.message = "You have successfully approved user!";
             	$scope.messageModal($scope.message);
             	loadUsers();
            });
			$uibModalInstance.dismiss('cancel');
		};
		$scope.cancel = function() {
			$uibModalInstance.dismiss('cancel');
		};

	} ];
    
    $scope.messageModal = function (message) {
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
    
    var WarningCtrl = ['$rootScope', '$scope', '$uibModalInstance', 'message', 'Restangular', '$log',
     function ($rootScope, $scope, $uibModalInstance, message,
			Restangular, $log) {
		$scope.message = message;
		$scope.ok = function() {
			$uibModalInstance.dismiss('cancel');
		};
		$scope.cancel = function() {
			$uibModalInstance.dismiss('cancel');
		};

	} ];
    
    
	loadUsers();
}]);