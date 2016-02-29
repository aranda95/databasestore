'use strict';

angular.module('databasestoreApp')
	.controller('CalentadorDeleteController', function($scope, $uibModalInstance, entity, Calentador) {

        $scope.calentador = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Calentador.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
