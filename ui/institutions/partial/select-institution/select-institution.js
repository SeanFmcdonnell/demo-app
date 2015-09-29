angular.module('institutions').controller('SelectInstitutionCtrl', function($scope, $modalInstance, institutions, accounts){

    $scope.search = null;
    $scope.total = 0;
    $scope.page= 1;
    $scope.institutions = [];
    $scope.state = 'select';

    function update() {
        var search = $scope.search;
        var page = $scope.page;

        if (search === null || search === '') {
            return;
        }

        institutions.getInstitutions(page, search).then(function(result) {
            $scope.total = result.data.found;
            $scope.institutions = result.data.institution;
        });
    }

    $scope.searchChange = function(search) {
        $scope.search = search;
        $scope.setPage(1);
    };

    $scope.setPage = function(page) {
        $scope.page = page;
        update();
    };

    $scope.selectBank = function(id) {
        $scope.state = 'login';
        $scope.institution = id;
        institutions.getLogin(id).then(function(result) {
            $scope.login = result.data;
        });
    };

    $scope.progress = function() {
        if ($scope.state === 'login') {
            var id = $scope.institution;
            institutions.discoverAccounts(id, $scope.login)
                .then(function(result) {
                    var data = result.data;
                    if (data['account']) {
                        accounts.addAccounts(data['account']);
                        $modalInstance.dismiss();
                    }
                    else {
                        $scope.state = 'mfa';
                        $scope.progress();
                    }
                });
        } else if ($scope.state === 'mfa') {

        }
    };
});
