angular.module('institutions').factory('institutions', function($http, host, auth) {

	var base = host + '/institutions/';

	var institutions = {
		getInstitutions: function(page, search) {
			return $http.get(base + '?page=' + page + '&search=' + search);
		},

		getLogin: function(id) {
			return $http.get(base + id + '/login');
		},

		discoverAccounts: function(id, login, type) {
			return $http({
				method: 'POST',
				url: base + type + '/' + id + '/discover',
				headers: {
					'Authorization': 'Bearer ' + auth.getToken()
				},
				data: login
			});
		},

		discoverAccountsMfa: function(id, questions, type) {
			return $http({
				method: 'POST',
				url: base + type + '/' + id + '/discover/mfa',
				headers: {
					'Authorization': 'Bearer ' + auth.getToken()
				},
				data: {
					question: questions
				}
			});
		}
	};

	return institutions;
});