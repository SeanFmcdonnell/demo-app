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
            console.log('Got transactions', result.data.transaction);
            $scope.transactions = result.data.transaction || [];
        });
        if (subscription !== null) {
            subscriptions.delete(subscription.id);
        }
        subscriptions.create(value).then(function(result) {
            subscription = result.data.subscription[0];
        });
    });

    $scope.open = function() {
        $modal.open({
            templateUrl: 'institutions/partial/select-institution/select-institution.html',
            controller: 'SelectInstitutionCtrl',
            resolve: {
                type: function() {
                    return $scope.custType;
                }
            }
        });
    };

    $scope.setActive = function(id) {
        $scope.active = id;
    };
});
