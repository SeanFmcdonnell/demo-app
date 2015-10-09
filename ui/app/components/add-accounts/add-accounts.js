angular.module('add.accounts').controller('AddAccountsCtrl', function($scope, $modalInstance, institutions,
    accounts, type) {

    $scope.search = null;
    $scope.total = 0;
    $scope.page = 1;
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

    function discover(result) {
        var data = result.data;
        if (data.code) {
            $scope.error = data;
            $scope.state = 'error';
        } else if (data.account) {
            accounts.addAccounts(data);
            $modalInstance.dismiss();
        } else if (data.question) {
            $scope.state = 'mfa';
            console.log(data.question);
            $scope.questions = data.question;
        }
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
        var id = $scope.institution;
        if ($scope.state === 'login') {
            institutions.discoverAccounts(id, $scope.login, type).then(discover);
        } else if ($scope.state === 'mfa') {
            console.log('Response', $scope.questions);
            institutions.discoverAccountsMfa(id, $scope.questions, type).then(discover);
        }
    };
});