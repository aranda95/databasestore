'use strict';

angular.module('databasestoreApp')
	.controller('AzulejoDeleteController', function($scope, $uibModalInstance, entity, Azulejo) {

        $scope.azulejo = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Azulejo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
