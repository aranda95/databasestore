'use strict';

angular.module('databasestoreApp').controller('PlatoDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Plato', 'Producto',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Plato, Producto) {

        $scope.plato = entity;
        $scope.productos = Producto.query({filter: 'plato-is-null'});
        $q.all([$scope.plato.$promise, $scope.productos.$promise]).then(function() {
            if (!$scope.plato.producto || !$scope.plato.producto.id) {
                return $q.reject();
            }
            return Producto.get({id : $scope.plato.producto.id}).$promise;
        }).then(function(producto) {
            $scope.productos.push(producto);
        });
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
