/*globals EventSource */
angular.module('institutions').factory('subscriptions', function($http, auth, host) {

	var base = host + '/subscriptions/';

	var subscriptions = {
		registerListener: function(events) {
			var url = base + 'events?token=' + auth.getToken();
			console.log('Regstering for events at', url);
			var source = new EventSource(url);
			events.forEach(function(event) {
				source.addEventListener(event.name, event.callback);
			});
		},

		create: function(accountId, type) {
			return $http({
				method: 'POST',
				url: base + type,
				headers: {
					'Authorization': 'Bearer ' + auth.getToken()
				},
				data: accountId
			}).then(function(result) {
				console.log(result);
				return result;
			}, function(err) {
				console.log(err);
				return err;
			});
		},

		delete: function(subscriptionId, type) {
			return $http({
				method: 'DELETE',
				url: base + type + '/' + subscriptionId,
				headers: {
					'Authorization': 'Bearer ' + auth.getToken()
				},
			}).then(function(result) {
				console.log(result);
				return result;
			}, function(err) {
				console.log(err);
				return err;
			});
		}
	};

	return subscriptions;
});