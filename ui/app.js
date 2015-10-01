var host = 'https://localhost/api';

var example = angular.module('example', ['ui.bootstrap', 'ui.utils', 'ngRoute', 'ngAnimate', 'institutions']);

angular.module('example').constant('host', host);

angular.module('example').config(function($routeProvider) {

    /* Add New Routes Above */
    $routeProvider.otherwise({redirectTo:'/home'});

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
      angular.element('html').injector().get('accounts').init();
    } else {
      console.log('Error signing in', xhr.status, xhr.responseText);
    }
  };
  xhr.send(id_token);
}
