'use strict';

angular.module('DreamTeam', [
    'ngResource',
    'ngRoute',
    'ui.bootstrap',
    'restangular',
    'dndLists',
    'angular-jwt'
]).constant('_', _
).config(['$httpProvider', '$routeProvider',
    function ($httpProvider, $routeProvider) {
	
	$routeProvider
	.when('/', {
        templateUrl: '/../images/home.html',
        controller: 'HomeController',
        requireLogin: true
    }).when('/create', {
        templateUrl: '/../images/createTeam.html',
        controller: 'CreateTeamController',
        requireLogin: true
    }).when('/dreamteam/:id', {
        templateUrl: '/../images/details.html',
        controller: 'TeamDetailsController',
        requireLogin: true
    }).when('/login', {
        templateUrl: '/../images/login.html',
        controller: 'LoginController'
    }).when('/admin/users', {
        templateUrl: '/../images/users.html',
        controller: 'UsersController',
        requireAdmin: true
    }).when('/admin/users/pending', {
        templateUrl: '/../images/pendingUsers.html',
        controller: 'ApproveController',
        requireAdmin: true
    }).when('/register', {
        templateUrl: '/../images/registration.html',
        controller: 'RegisterController'
    })
    .otherwise({
        redirectTo: '/'
    });
	
	
}]).run(['$http','$log', '$rootScope', 'Restangular', 'authService', function ($http, $log, $rootScope, Restangular, authService) {
	
	Restangular.setBaseUrl("/api");
	
	$rootScope.$on("$routeChangeStart", function(event, next, current) {
		//If page requires admin authority and current user authority is not admin, redirect him on homepage
		if(next.requireAdmin && !authService.isAdmin())
			window.location = "/#/";
		//Redirect to login page if user is not logged in
		if(next.requireLogin && !authService.isLoggedIn())
			window.location = "/#/login";
    });
}]);