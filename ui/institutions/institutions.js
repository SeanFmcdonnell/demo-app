angular.module('institutions', ['ui.bootstrap','ui.utils','ngRoute','ngAnimate']);

angular.module('institutions').config(function($routeProvider) {

    /* Add New Routes Above */

});

angular.module('institutions').controller('InstitutionsCtrl', function($scope, $modal, accounts, subscriptions, auth) {

    $scope.accounts = accounts.getAccounts();
    $scope.active = accounts[0];
    $scope.transactions = [];
    var subscription = null;

    $scope.$watch(function() {
        console.log('Called watch');
        return accounts.getAccounts();
    }, function(value) {
        $scope.accounts = value;
        if (!$scope.active) {
            $scope.active = value[0];
        }
    }, true);

    $scope.$watch(function() {
        if (!$scope.active) {
            return undefined;
        }
        return $scope.active.id;
    }, function(value) {
        if (!value) {
            return;
        }
        accounts.getTransactions(value).then(function(result) {
            $scope.transactions = result.data.transaction;
        });
        if (subscription !== null) {
            subscriptions.delete(subscription.id);
        }
        subscriptions.create(value).then(function(result) {
            subscription = result.data.subscription[0];
        });
    });

    $scope.$watch(function() {
        return auth.getToken();
    }, function(value) {
        if (!value) {
            return;
        }
        $scope.loggedIn = true;
        subscriptions.registerListener('transaction', function(event) {
            var transaction = JSON.parse(event.data);
            if ($scope.active && transaction.accountId === $scope.active.id) {
                $scope.transactions.push(transaction);
            }
        });
    });

    $scope.open = function() {
        $modal.open({
            templateUrl: 'institutions/partial/select-institution/select-institution.html',
            controller: 'SelectInstitutionCtrl'
        });
    };

    $scope.setActive = function(id) {
        $scope.active = id;
    };
});
