angular.module('institutions').factory('institutions', function($rootScope, $http, host, auth) {

	var base = host + '/institutions/';

	function start() {
		$rootScope.loading = true;
	}

	function stop() {
		$rootScope.loading = false;
	}

	var institutions = {
		getInstitutions: function(page, search) {
			return $http.get(base + '?page=' + page + '&search=' + search);
		},

		getLogin: function(id) {
			return $http.get(base + id + '/login');
		},

		discoverAccounts: function(id, login, type) {
			start();
			return $http({
				method: 'POST',
				url: base + type + '/' + id + '/discover',
				headers: {
					'Authorization': 'Bearer ' + auth.getToken()
				},
				data: login
			}).then(function(result) {
				stop();
				console.log(result);
				return result;
			}, function(err) {
				stop();
				console.log(err);
				return err;
			});
		},

		discoverAccountsMfa: function(id, questions, type) {
			start();
			return $http({
				method: 'POST',
				url: base + type + '/' + id + '/discover/mfa',
				headers: {
					'Authorization': 'Bearer ' + auth.getToken()
				},
				data: {
					question: questions
				}
			}).then(function(result) {
				stop();
				console.log(result);
				return result;
			}, function(err) {
				stop();
				console.log(err);
				return err;
			});
		}
	};

	return institutions;
});