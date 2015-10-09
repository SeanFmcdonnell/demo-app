var host = 'http://localhost:8080/api';

angular.module('main', ['ui.bootstrap', 'ui.utils', 'ngRoute', 'ngAnimate', 'ui-notification']);

angular.module('view.accounts', ['ui.bootstrap', 'ui.utils', 'ngRoute', 'ngAnimate']);

angular.module('add.accounts', ['ui.bootstrap', 'ui.utils', 'ngRoute', 'ngAnimate']);

angular.module('services', ['ui.bootstrap', 'ui.utils', 'ngRoute', 'ngAnimate']);

var app = angular.module('app', ['main', 'services', 'view.accounts', 'add.accounts']);

angular.module('app').constant('host', host);

angular.module('app').config(function($routeProvider) {

  /* Add New Routes Above */
  $routeProvider.otherwise({
    redirectTo: '/home'
  });

});

angular.module('app').run(function($rootScope) {

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