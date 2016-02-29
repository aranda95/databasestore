'use strict';

angular.module('databasestoreApp').controller('ProductoDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Producto', 'Calentador',
        function($scope, $stateParams, $uibModalInstance, entity, Producto, Calentador) {

        $scope.producto = entity;
        $scope.calentadors = Calentador.query();
        $scope.load = function(id) {
            Producto.get({id : id}, function(result) {
                $scope.producto = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('databasestoreApp:productoUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.producto.id != null) {
                Producto.update($scope.producto, onSaveSuccess, onSaveError);
            } else {
                Producto.save($scope.producto, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
