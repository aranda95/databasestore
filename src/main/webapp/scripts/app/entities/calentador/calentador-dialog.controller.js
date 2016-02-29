'use strict';

angular.module('databasestoreApp').controller('CalentadorDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Calentador', 'Producto',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Calentador, Producto) {

        $scope.calentador = entity;
        $scope.productos = Producto.query({filter: 'calentador-is-null'});
        $q.all([$scope.calentador.$promise, $scope.productos.$promise]).then(function() {
            if (!$scope.calentador.producto || !$scope.calentador.producto.id) {
                return $q.reject();
            }
            return Producto.get({id : $scope.calentador.producto.id}).$promise;
        }).then(function(producto) {
            $scope.productos.push(producto);
        });
        $scope.load = function(id) {
            Calentador.get({id : id}, function(result) {
                $scope.calentador = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('databasestoreApp:calentadorUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.calentador.id != null) {
                Calentador.update($scope.calentador, onSaveSuccess, onSaveError);
            } else {
                Calentador.save($scope.calentador, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
