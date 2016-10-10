
'use strict';

angular.module('DreamTeam').controller('TeamDetailsController', ['$rootScope', '$http', '$scope', '$uibModal', '$log', 'Restangular', '_', '$routeParams',
    function ($rootScope, $http, $scope, $uibModal, $log, Restangular, _, $routeParams) {
	
	$scope.team = {};
	
	$scope.players = [];
	
	
	
	var loadTeam = function() {
		Restangular.one("/dreamteam/" + $routeParams.id).get().then(function (team) {
            $scope.team = team;
        });
	};
	
	var getPlayers = function(){
		Restangular.one("stats").get().then(function (entries) {
			var players = entries.players;
			for(var i = 0; i < $scope.team.players.length; i++){
				for(var j = 0; j < players.length; j++){
					if($scope.team.players[i] == players[j].id){
						$scope.players.push(players[j]);
						break;
					}
				}
			}
			var temp = $scope.players;
			for(var i = 0; i < temp.length; i++){
				var position = "";
				for(var j = 0; j < temp[i]['pos'].length; j++){
					position = position + temp[i]['pos'][j] + " ";
				}
				temp[i]['pos'] = position;
			}
			$scope.players = temp;
        });
	}
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
                var name = $scope.team.name + ".pdf"
                pdfMake.createPdf(docDefinition).download(name);
            }
        });
	}
	$scope.excel = function(){
		var path = "http://localhost:8080/api/export/dreamteam/" + $routeParams.id;
		$http.get(path, { responseType: 'arraybuffer' })
		  .success(function(data) {
			  	var blob = new Blob([data], {type: 'application/vnd.ms-excel'});
		        var downloadUrl = URL.createObjectURL(blob);
		        var a = document.createElement("a");
		        a.href = downloadUrl;
		        a.download = $scope.team.name + ".xls";
		        document.body.appendChild(a);
		        a.click();
		  });
	}
	loadTeam();
	getPlayers();
}]);