'use strict';

angular.module('databasestoreApp')
	.controller('TekaDeleteController', function($scope, $uibModalInstance, entity, Teka) {

        $scope.teka = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Teka.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
