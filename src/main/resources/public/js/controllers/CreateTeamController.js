
'use strict';

angular.module('DreamTeam').controller('CreateTeamController', ['$rootScope', '$http', '$scope', '$uibModal', '$log', 'Restangular', '_', 'authService',
    function ($rootScope, $http, $scope, $uibModal, $log, Restangular, _, authService) {
	
	
	$scope.teamName = "";
	
	$scope.message = "";
	
	$scope.stats = [];
	
	$scope.position = 1;
	$scope.player = {
			position: "Point Guard"
	}
	
	$scope.allPlayers = [];
	
	$scope.searchText = "";
	$scope.tempPlayers = [];
	
	getAllPlayers();
	
	getStats();
	
	$scope.dnd = { 
		selectedPlayer: null,
		lists: {
			listOfAttendingPlayers: [],
			players: []
	        
		}
        
	};
	
	function getAllPlayers(){
		Restangular.one("players").get().then(function (entries) {
			$scope.allPlayers = entries.players;
			$scope.dnd.lists.players = _.filter($scope.allPlayers, function(o) { return _.includes(o.pos, "PG")});
			$scope.tempPlayers = $scope.dnd.lists.players;
        });
	}
	
	function getStats(){
		Restangular.one("stats").get().then(function (entries) {
			$scope.stats = entries.players;
        });
	}
	
	$scope.search = function(searchText){
		$scope.dnd.lists.players = $scope.tempPlayers;
		if(searchText.length == 0){
			$scope.dnd.lists.players = $scope.tempPlayers;
		}else if(searchText.length > 2){
			searchText = searchText.toLowerCase();
			$scope.dnd.lists.players = _.filter($scope.dnd.lists.players, function(o){
				var name = o.firstname + " " + o.lastname;
				name = name.toLowerCase();
				return (_.includes(name, searchText)) 
				});
		}
	}
	
	$scope.filterPlayers = function(val){
		$scope.position += val;
		if($scope.position == 1){
			$scope.dnd.lists.players = _.filter($scope.allPlayers, function(o) { return _.includes(o.pos, 'PG')});
			$scope.tempPlayers = $scope.dnd.lists.players;
			$scope.player.position = "Point Guard";
		}else if($scope.position == 2){
			$scope.dnd.lists.players = _.filter($scope.allPlayers, function(o) { return _.includes(o.pos, 'SG')});
			$scope.tempPlayers = $scope.dnd.lists.players;
			$scope.player.position = "Shooting Guard";
		}else if($scope.position == 3){
			$scope.dnd.lists.players = _.filter($scope.allPlayers, function(o) { return _.includes(o.pos, 'SF')});
			$scope.tempPlayers = $scope.dnd.lists.players;
			$scope.player.position = "Small Forward";
		}else if($scope.position == 4){
			$scope.dnd.lists.players = _.filter($scope.allPlayers, function(o) { return _.includes(o.pos, 'PF')});
			$scope.tempPlayers = $scope.dnd.lists.players;
			$scope.player.position = "Power Forward";
		}else if($scope.position == 5){
			$scope.dnd.lists.players = _.filter($scope.allPlayers, function(o) { return _.includes(o.pos, 'C')});
			$scope.tempPlayers = $scope.dnd.lists.players;
			$scope.player.position = "Center";
		}
		
		//Remove player that already is on dream team from all players, so user couldn't add same player twice
		for(var i = 0, list = $scope.dnd.lists.listOfAttendingPlayers; i < list.length; i ++){
			_.remove($scope.dnd.lists.players, function(n) { return n.id == list[i].id});
		}
	}
	
	//Add team
	$scope.saveTeam = function(teamName){
		if($scope.dnd.lists.listOfAttendingPlayers.length != 5){
			$scope.message = "Your team mush have exactly 5 members!";
			$scope.openModal($scope.message);
		}else if(teamName == ""){
			$scope.message = "You did not pick team name!";
			$scope.openModal($scope.message);
		}else{
			
			var positions = {"PG" : [], "SG": [], "SF": [], "PF": [], "C": []};
			//Counts how many possible positions our team cover
			for (var i = 0, list = $scope.dnd.lists.listOfAttendingPlayers; i < list.length; i ++){
				for(var k =0, posList = list[i]['pos']; k < posList.length; k++){
					if(posList[k] == "PG")
						positions["PG"].push(list[i].id);
					else if(posList[k] == "SG")
						positions["SG"].push(list[i].id);
					else if(posList[k] == "SF")
						positions["SF"].push(list[i].id);
					else if(posList[k] == "PF")
						positions["PF"].push(list[i].id);
					else
						positions["C"].push(list[i].id);
				}
			}
			
			//Checks if user picked at least one player for every position
			var legit = true;
			for(var i in positions){
				for(var j in positions){
					if((positions[i].length == 1) && (positions[j].length == 1) && (i != j)){
						if(positions[i][0] == positions[j][0]){
							legit = false;
							break;
						}
					}
				}
			}
			//If we did not cover every position or we have one player covers two positions, warning is printed
			if((!legit) || (positions["PG"].length == 0)  || (positions["SG"].length == 0) || (positions["SF"].length == 0)  || (positions["PF"].length == 0) || (positions["C"].length == 0)){
				$scope.message = "You must have one player on each position!";
				$scope.openModal($scope.message);
			}else{
				var successIndex = 0;
				var players = [];
				for(var i = 0; i < $scope.dnd.lists.listOfAttendingPlayers.length; i++){
					players.push($scope.dnd.lists.listOfAttendingPlayers[i]["id"]);
					for(var j = 0; j < $scope.stats.length; j++){
						if($scope.dnd.lists.listOfAttendingPlayers[i]["id"] == $scope.stats[j]["id"]){
							var player = $scope.stats[j];
							var fg = player["fga"] - player["fgm"];
							var ft = player["fta"] - player["ftm"];
							var index = player["pts"] + player["reb"] + player["asts"] + player["stl"] + player["blk"] - player["to"] - fg - ft;
							successIndex += index;
						}
					}
				}
				var dreamTeam = {
						"name": teamName,
						"successIndex": successIndex,
						"players": players
				}
				console.log(dreamTeam);
				Restangular.all('dreamteam').post(dreamTeam).then(function (data) {
					console.log("success");
					$scope.successMessage();
		        });
			}
			
			
			
			
		}
		
	}
	
	//Opening modal in case of wrong user's input
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
    
    //Success message modal, when new team is added
    $scope.successMessage = function () {
        var modalInstance = $uibModal.open({
            templateUrl: '/../../images/success.html',
            controller: SuccessCtrl,
            scope: $scope,
            resolve: {
            }
        });
        modalInstance.result.then(function (value) {
        }, function (value) {
        });
    };
    
    var SuccessCtrl = ['$scope', '$uibModalInstance', 'Restangular', '$log',
     function ($scope, $uibModalInstance, Restangular, $log) {
         $scope.ok = function () {
        	 $uibModalInstance.dismiss('cancel');
        	 location.reload();
         };
         $scope.cancel = function () {
             $uibModalInstance.dismiss('cancel');
             location.reload();
         };

     }];
	
	
	
}]);