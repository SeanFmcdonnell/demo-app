/*globals EventSource */
angular.module('institutions').factory('subscriptions',function($http, auth, host) {

	var base = host + '/subscriptions/';

	var subscriptions = {
        registerListener: function(event, func){
			var url = base + 'events?token=' + auth.getToken();
			console.log('Regstering for events at', url)
            var source = new EventSource(url);
            source.addEventListener(event, func);
        },

        create: function(accountId) {
            return $http({
				method: 'POST',
				url: base,
				headers: {
					'Authorization' : 'Bearer ' + auth.getToken()
				},
				data: accountId
			});
        },

        delete: function(subscriptionId) {
            return $http({
				method: 'DELETE',
				url: base + subscriptionId,
				headers: {
					'Authorization' : 'Bearer ' + auth.getToken()
				},
			});
        }
    };

	return subscriptions;
});
