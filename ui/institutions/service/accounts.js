angular.module('institutions').factory('accounts', function($http, auth, host) {

  var _accounts = [];
  var base = host + '/accounts/';

  var _institutions = [];

  function addAccounts(data) {
    _institutions = data.institutions;
    data.account = (data.account) ? data.account : [];
    data.account.forEach(function(account) {
      console.log('Adding account', account);
      _accounts.push(account);
    });
  }

  function resetAccounts() {
    _accounts = [];
  }

  function refresh(type) {
    return $http({
      method: 'POST',
      url: base + type + '/refresh',
      headers: {
        'Authorization': 'Bearer ' + auth.getToken()
      }
    }).then(function(result) {
      _institutions = result.data.institutions;
    });
  }

  function refreshAccount(id, type) {
    return $http({
      method: 'POST',
      url: base + type + '/refresh/' + id,
      headers: {
        'Authorization': 'Bearer ' + auth.getToken()
      }
    }).then(function(result) {
      console.log(result);
    });
  }

  function init(type) {
    $http({
      method: 'GET',
      url: base + type,
      headers: {
        'Authorization': 'Bearer ' + auth.getToken()
      }
    }).then(function(result) {
      console.log(result.data);
      addAccounts(result.data);
    });
  }

  function getAccounts() {
    return _accounts;
  }

  function remove(id, type) {
    $http({
      method: 'DELETE',
      url: base + type + '/' + id,
      headers: {
        'Authorization': 'Bearer ' + auth.getToken()
      }
    }).then(function(result) {
      resetAccounts();
      addAccounts(result.data);
    });
  }

  function removeAll(fid, type) {
    accounts.getAccounts().filter(function(obj) {
      return obj.institutionId === fid;
    }).forEach(function(account, ind, arr) {
      $http({
        method: 'DELETE',
        url: base + type + '/' + account.id,
        headers: {
          'Authorization': 'Bearer ' + auth.getToken()
        }
      }).then(function(result) {
        if (ind === arr.length - 1) {
          resetAccounts();
          addAccounts(result.data);
          refresh(type);
        }
      });
    });
  }

  function getTransactions(id, type) {
    return $http({
      method: 'GET',
      url: base + type + '/' + id + '/transactions',
      headers: {
        'Authorization': 'Bearer ' + auth.getToken()
      }
    });
  }

  function getInstitutions() {
    return _institutions;
  }

  var accounts = {
    addAccounts: addAccounts,

    Drefresh: refresh,

    refreshAccount:refreshAccount,

    resetAccounts: resetAccounts,

    init: init,

    getAccounts: getAccounts,

    remove: remove,

    removeAll: removeAll,

    getTransactions: getTransactions,

    getInstitutions: getInstitutions
  };

  return accounts;
});