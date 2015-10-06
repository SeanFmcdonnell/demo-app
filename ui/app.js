var host = 'http://localhost:8080/api';

var example = angular.module('example', ['ui.bootstrap', 'ui.utils', 'ngRoute', 'ngAnimate', 'institutions']);

angular.module('example').constant('host', host);

angular.module('example').config(function($routeProvider) {

  /* Add New Routes Above */
  $routeProvider.otherwise({
    redirectTo: '/home'
  });

});

angular.module('example').run(function($rootScope) {

  $rootScope.safeApply = function(fn) {
    var phase = $rootScope.$$phase;
    if (phase === '$apply' || phase === '$digest') {
      if (fn && (typeof(fn) === 'function')) {
        fn();
      }
    } else {
      this.$apply(fn);
    }
  };
});

angular.module('example').controller('AppCtrl', function($scope, $modal, accounts, subscriptions, auth) {

  $scope.$watch(function() {
    return auth.getToken();
  }, function(value) {
    if (!value) {
      return;
    }
    console.log('LogIn');
    $scope.loggedIn = true;
    subscriptions.registerListener('transaction', function(event) {
      var transaction = JSON.parse(event.data);
      console.log('Got transaction event', transaction);
      if ($scope.active && transaction.accountId === $scope.active.id) {
        $scope.transactions.unshift(transaction);
        $scope.$apply();
      }
    });
  });

  $scope.tabChange = function(type) {
    $scope.custType = type;
    accounts.resetAccounts();
    accounts.init(type);
  };

});

$(document).ready(function() {
  $('[data-toggle=offcanvas]').click(function() {
    $('.row-offcanvas').toggleClass('active');
  });
});

function onSignIn(googleUser) {
  // Not secure. Don't do this in production
  var id_token = googleUser.getBasicProfile().getId();
  var xhr = new XMLHttpRequest();
  xhr.open('POST', host + '/google/signin');
  xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
  xhr.onload = function() {
    if (xhr.status === 200) {
      var token = xhr.responseText;
      angular.element('html').injector().get('auth').setToken(token);
      angular.element('html').injector().get('accounts').init('active');
    } else {
      console.log('Error signing in', xhr.status, xhr.responseText);
    }
  };
  xhr.send(id_token);
}

function signOut() {
  gapi.auth2.getAuthInstance().signOut().then(function() {
    console.log('Signed out.');
    window.location.reload();
  });
}