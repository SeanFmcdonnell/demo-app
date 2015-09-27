angular.module('institutions').factory('institutions',function($http, host) {

	var token;
	var base = host + '/institutions/';

	var institutions = {
		setToken: function(response) {
			token = response;
			console.log('Token set to', response);
		},

		getInstitutions: function(page, search) {
	        return $http.get(base + '?page=' + page + '&search=' + search);
		},

		getLogin: function(id) {
            return $http.get(base + id + '/login');
		},

		discoverAccounts(id, login) {
            return $http({
				method: 'POST',
				url: base + id + '/discover',
				headers: {
					'Authorization' : 'Bearer ' + token
				},
				data: login
			});
		}
	};

	return institutions;
});
