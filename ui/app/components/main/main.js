angular.module('main').config(function(NotificationProvider) {
  NotificationProvider.setOptions({
    delay: 25000
  });
});

angular.module('main').controller('MainPageCtrl', function($scope, $modal, Notification, accounts, subscriptions, auth) {

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