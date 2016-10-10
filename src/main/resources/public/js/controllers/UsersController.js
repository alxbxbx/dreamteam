
'use strict';

angular.module('DreamTeam').controller('UsersController', ['$rootScope', '$http', '$scope', '$uibModal', '$log', 'Restangular', '_', 'authService',
    function ($rootScope, $http, $scope, $uibModal, $log, Restangular, _, authService) {
	
	var initialize = function(){
		$scope.show = true;
		$scope.users = [];
		$scope.user = {
				username: "",
				name: "",
				lastname: "",
				password: "",
				passwordAgain: "",
				role: "",
				email: ""
		};
		$scope.message = "";
	}
	
	
	
	var loadUsers = function (){
		Restangular.all("users").getList().then(function (entries) {
            $scope.users = entries;
        });
	}
	
	$scope.openModal = function (user) {
		//If we passed empty user - we want to create user, so approve and active checkboxes will be disabled and both will set to default value - true
		if(typeof user == "undefined"){
			$scope.show = false;
			$scope.user.active = true;
			$scope.user.approved = true;
		}else{
			$scope.show = true;
		}
        var modalInstance = $uibModal.open({
            templateUrl: '/../../images/userModal.html',
            controller: UserCtrl, 
            scope: $scope,
            resolve: {
                user: function () {
                    return user;
                }
            }
        });
        modalInstance.result.then(function (value) {
        	loadUsers();
        }, function (value) {
        });
    };
    
    var UserCtrl = ['$rootScope','$scope', '$uibModalInstance', 'user', 'Restangular', '$log', '_', 'authService',
     function ($rootScope, $scope, $uibModalInstance, user, Restangular, $log, _, authService) {
    	if(typeof user != "undefined"){
    		$scope.user = user;
    	}
    	 $scope.ok = function () {
    		 if(($scope.user.name == "") || ($scope.user.lastName == "") || ($scope.user.username == "") || ($scope.user.role == "")  || (typeof $scope.user.active == "undefined") || (typeof $scope.user.approved == "undefined")){
	     		console.log($scope.user);
	     		$scope.message = "You must fill all fields!";
	     		$scope.messageModal($scope.message);
	     	}else if((typeof $scope.user.email == "undefined") || ($scope.user.email == "")){
	     		$scope.message = "Email is not correct!";
	     		$scope.messageModal($scope.message);
	     	}
	     	else{
	     		if ($scope.user.id) {
	                 Restangular.all('users').customPUT($scope.user).then(function (data) {
	                	 if($rootScope.username == $scope.user.username){
	                		$scope.message = "You have successfully updated your profile!";
	                  		$scope.messageModal($scope.message);
	                	 }else{
	                		$scope.message = "Update was successful!";
	                   		$scope.messageModal($scope.message); 
	                	 }
	                 });
	                 loadUsers();
	             } else {
	            	 console.log($scope.user);
	                 Restangular.all('users').post($scope.user).then(function (data) {
	                	 $scope.message = "You have successfully added new user.";
	                	 $scope.messageModal($scope.message); 
	                	 initialize();
	                	 loadUsers();
	                 },
	                 function (response) {
	                     console.log(response);
	                 });
	             }
	     		$uibModalInstance.close('ok');  
	             
	     	}
         };

         $scope.cancel = function () {
             $uibModalInstance.dismiss('cancel');
         };


     }];
    
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
    
    $scope.deleteUser = function (id) {
        var modalInstance = $uibModal.open({
            templateUrl: '/../../images/deleteModal.html',
            controller: DeleteCtrl,
            scope: $scope,
            resolve: {
                id: function () {
                    return id;
                }
            }
        });
        modalInstance.result.then(function (value) {
        }, function (value) {
        });
    };
    
    var DeleteCtrl = ['$rootScope', '$scope', '$uibModalInstance', 'id', 'Restangular', '$log',
     function ($rootScope, $scope, $uibModalInstance, id,
			Restangular, $log) {
		$scope.ok = function() {
			Restangular.one("users", id).remove().then(function () {
                loadUsers();
            });
			$uibModalInstance.dismiss('cancel');
		};
		$scope.cancel = function() {
			$uibModalInstance.dismiss('cancel');
		};

	} ];
    
    $scope.passwordModal = function (user) {
        var modalInstance = $uibModal.open({
            templateUrl: '/../../images/passwordModal.html',
            controller: PasswordCtrl,
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
    
    var PasswordCtrl = ['$rootScope', '$scope', '$uibModalInstance', 'user', 'Restangular', '$log',
     function ($rootScope, $scope, $uibModalInstance, user,
			Restangular, $log) {
		$scope.user = user;
		$scope.ok = function() {
			if($scope.user.password != $scope.user.passwordAgain){
	     		$scope.message = "Passwodrs did not match!";
	     		$scope.messageModal($scope.message);
	     	}else{
	     		Restangular.all('users/changepassword').customPUT($scope.user).then(function (data) {
               		$scope.message = "You have successfully updated password!";
                 	$scope.messageModal($scope.message);
                });
                loadUsers();
	     	}
			$uibModalInstance.dismiss('cancel');
		};
		$scope.cancel = function() {
			$uibModalInstance.dismiss('cancel');
		};

	} ];
    
    $scope.pdf = function(){
		html2canvas(document.getElementById('export'), {
            onrendered: function (canvas) {
                var data = canvas.toDataURL();
                var docDefinition = {
                    content: [{
                        image: data,
                        width: 500,
                    }]
                };
                var name = "List_of_users.pdf"
                pdfMake.createPdf(docDefinition).download(name);
            }
        });
	}
    
    $scope.excel = function(){
		var path = "http://localhost:8080/api/export/users";
		$http.get(path, { responseType: 'arraybuffer' })
		  .success(function(data) {
			  	var blob = new Blob([data], {type: 'application/vnd.ms-excel'});
		        var downloadUrl = URL.createObjectURL(blob);
		        var a = document.createElement("a");
		        a.href = downloadUrl;
		        a.download = "List_of_users.xls";
		        document.body.appendChild(a);
		        a.click();
		  });
	}
    
    
	
	loadUsers();
	initialize();
} ]);