'use strict';

angular.module('databasestoreApp').controller('GrifoDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Grifo', 'Producto',
        function($scope, $stateParams, $uibModalInstance, entity, Grifo, Producto) {

        $scope.grifo = entity;
        $scope.productos = Producto.query();
        $scope.load = function(id) {
            Grifo.get({id : id}, function(result) {
                $scope.grifo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('databasestoreApp:grifoUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.grifo.id != null) {
                Grifo.update($scope.grifo, onSaveSuccess, onSaveError);
            } else {
                Grifo.save($scope.grifo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
