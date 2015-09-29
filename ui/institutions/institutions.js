angular.module('institutions', ['ui.bootstrap','ui.utils','ngRoute','ngAnimate']);

angular.module('institutions').config(function($routeProvider) {

    /* Add New Routes Above */

});

angular.module('institutions').controller('InstitutionsCtrl', function($scope, $modal, accounts) {

    $scope.accounts = accounts.getAccounts();
    $scope.active = accounts[0];

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
        accounts.getTransactions(value).then(function(result) {
            $scope.transactions = result.data.transaction;
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
