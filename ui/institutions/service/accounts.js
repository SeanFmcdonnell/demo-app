angular.module('institutions').factory('accounts',function($http, auth, host) {

    var _accounts = [];
	var base = host + '/accounts/';

    function addAccounts(list) {
		list.forEach(function(account) {
			console.log('Adding account', account);
			_accounts.push(account);
		});
    }

    function refresh(id) {
            return $http({
                method: 'POST',
                url: base + 'refresh',
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

        getTransactions: function(id) {
            return $http({
                method: 'GET',
                url: base + id + '/transactions',
				headers: {
					'Authorization' : 'Bearer ' + auth.getToken()
				}
            });
        },

		init: function() {
            $http({
                method: 'GET',
                url: base,
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
