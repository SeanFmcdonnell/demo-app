angular.module('institutions', ['ui.bootstrap','ui.utils','ngRoute','ngAnimate']);

angular.module('institutions').config(function($routeProvider) {

    /* Add New Routes Above */

});

angular.module('institutions').controller('InstitutionsCtrl', function($scope, $modal) {

    $scope.open = function() {
        $modal.open({
            templateUrl: 'institutions/partial/select-institution/select-institution.html',
            controller: 'SelectInstitutionCtrl'
        });
    };
})
