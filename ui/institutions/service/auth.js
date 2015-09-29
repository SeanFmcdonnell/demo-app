angular.module('institutions').factory('auth',function() {

	var token;

	var auth = {
		getToken: function() {
			return token;
		},

		setToken: function(response) {
			token = response;
			console.log('Token set to', response);
		}
	};

	return auth;
});
