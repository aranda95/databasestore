'use strict';

angular.module('databasestoreApp')
	.controller('SanitarioDeleteController', function($scope, $uibModalInstance, entity, Sanitario) {

        $scope.sanitario = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Sanitario.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
