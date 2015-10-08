var host = 'http://localhost:8080/api';

var example = angular.module('example', ['ui.bootstrap', 'ui.utils', 'ngRoute', 'ngAnimate', 'institutions',
  'ui-notification'
]);

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

angular.module('example').config(function(NotificationProvider) {
  NotificationProvider.setOptions({
    delay: 25000
  });
});

angular.module('example').controller('AppCtrl', function($scope, $modal, Notification, accounts, subscriptions, auth) {

  $scope.$watch(function() {
    return auth.getToken();
  }, function(value) {
    if (!value) {
      return;
    }
    console.log('LogIn');
    $scope.loggedIn = true;
    subscriptions.registerListener([{
      name: 'transaction',
      callback: transactionCallback
    }, {
      name: 'account',
      callback: accountCallback
    }]);
  });

  function transactionCallback(event) {
    var transaction = JSON.parse(event.data);
    console.log('Got transaction event', transaction);

    var msg = ['Amount: $', transaction.amount, ' | Customer Id: ', transaction.customerId, '\nDescription: ',
      transaction.description
    ];

    Notification.primary({
      title: 'Recieved Transaction for ' + transaction.accountName,
      message: msg.join('')
    });

    accounts.transactions.addToQueue(transaction);
  }

  function accountCallback(event) {
    var account = JSON.parse(event.data);
    console.log('Got account event', account);

    var msg = ['Balance: $', account.balance, ' | Customer Id: ', account.customerId];

    Notification.primary({
      title: 'Recieved Account Notification for ' + account.name,
      message: msg.join('')
    });

    accounts.updateAccountBalance(account);
  }

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
      var doc = xhr.responseXML.getElementsByTagName('AuthResponse')[0];
      var token = doc.getElementsByTagName('token')[0].textContent;
      var activeId = doc.getElementsByTagName('activeId')[0].textContent;
      var testingId = doc.getElementsByTagName('testingId')[0].textContent;
      angular.element('html').injector().get('auth').setToken(token, activeId, testingId);
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
