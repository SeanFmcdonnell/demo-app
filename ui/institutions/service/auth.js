angular.module('institutions').factory('auth',function($rootScope) {

	var token;

	var auth = {
		getToken: function() {
			return token;
		},

		setToken: function(response, activeId, testingId) {
			token = response;
			console.log('Token set to', response);

			$rootScope.activeId = activeId;
			$rootScope.testingId = testingId;
		}
	};

	return auth;
});
