angular.module('institutions', ['ui.bootstrap', 'ui.utils', 'ngRoute', 'ngAnimate']);

angular.module('institutions').config(function($routeProvider) {

  /* Add New Routes Above */

});

angular.module('institutions').controller('InstitutionsCtrl', function($scope, $modal, accounts, subscriptions, auth) {

  $scope.accounts = accounts.getAccounts();
  $scope.active = accounts[0];
  $scope.transactions = [];
  var subscription = null;

  $scope.$watch(function() {
    return accounts.transactions.getQueue();
  }, function(value) {
    value.forEach(function(transaction) {
      if(transaction.accountId === $scope.active.id) {
        $scope.transactions.push(transaction);
        accounts.transactions.removeFromQueue(transaction.id);
      }
    });
  });

  $scope.$watch(function() {
    return accounts.getAccounts();
  }, function(value) {
    $scope.accounts = value;
    if (value.length === 0) {
      $scope.active = undefined;
      $scope.institutions = [];
    }
    if (value.indexOf($scope.active) === -1) {
      $scope.active = undefined;
    }
    if (!$scope.active) {
      $scope.active = value[0];
    }
  }, true);

  $scope.$watch(function() {
    return accounts.getInstitutions();
  }, function(value) {
    $scope.institutions = value;
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
    accounts.transactions.get(value, $scope.custType).then(function(result) {
      console.log('Got transactions', result.data.transaction);
      $scope.transactions = result.data.transaction || [];
    });
    if (subscription !== null) {
      subscriptions.delete(subscription.id);
    }
    subscriptions.create(value, $scope.custType).then(function(result) {
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

  $scope.deleteAccount = function(id) {
    accounts.remove(id, $scope.custType);
  };

  $scope.deleteAll = function(ind, id, e) {
    if (e) {
      e.preventDefault();
      e.stopPropagation();
    }
    accounts.removeAll(id, $scope.custType);
  };

  $scope.refreshActive = function() {
    $scope.loading = true;
    accounts.refreshAccount($scope.active.id, $scope.custType).then(function(result) {
      $scope.loading = false;
    });
  };
});