'use strict';

angular.module('databasestoreApp')
	.controller('SacoDeleteController', function($scope, $uibModalInstance, entity, Saco) {

        $scope.saco = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Saco.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
