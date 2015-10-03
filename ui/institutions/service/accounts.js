angular.module('institutions').factory('accounts',function($http, auth, host) {

    var _accounts = [];
	var base = host + '/accounts/';

    function addAccounts(list) {
		list.forEach(function(account) {
			console.log('Adding account', account);
			_accounts.push(account);
		});
    }

    function refresh(id, type) {
            return $http({
                method: 'POST',
                url: base + type + '/refresh',
				headers: {
					'Authorization' : 'Bearer ' + auth.getToken()
				}
            });
    }

	var accounts = {
		addAccounts: addAccounts,

		getAccounts: function() {
			return _accounts;
		},

        refresh: refresh, 

        getTransactions: function(id, type) {
            return $http({
                method: 'GET',
                url: base + type + '/' + id + '/transactions',
				headers: {
					'Authorization' : 'Bearer ' + auth.getToken()
				}
            });
        },

		init: function(type) {
            $http({
                method: 'GET',
                url: base + type,
				headers: {
					'Authorization' : 'Bearer ' + auth.getToken()
				}
            }).then(function(result) {
                console.log(result.data);
                addAccounts(result.data.account || []);
                refresh();
            });
		}
	};

	return accounts;
});
