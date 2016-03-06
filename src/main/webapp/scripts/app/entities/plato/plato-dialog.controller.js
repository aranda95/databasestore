'use strict';

angular.module('databasestoreApp').controller('PlatoDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Plato', 'Producto',
        function($scope, $stateParams, $uibModalInstance, entity, Plato, Producto) {

        $scope.plato = entity;
        $scope.productos = Producto.query();
        $scope.load = function(id) {
            Plato.get({id : id}, function(result) {
                $scope.plato = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('databasestoreApp:platoUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.plato.id != null) {
                Plato.update($scope.plato, onSaveSuccess, onSaveError);
            } else {
                Plato.save($scope.plato, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
