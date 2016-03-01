'use strict';

angular.module('databasestoreApp')
	.controller('GrifoDeleteController', function($scope, $uibModalInstance, entity, Grifo) {

        $scope.grifo = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Grifo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
