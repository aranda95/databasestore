'use strict';

angular.module('databasestoreApp')
	.controller('PlatoDeleteController', function($scope, $uibModalInstance, entity, Plato) {

        $scope.plato = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Plato.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
