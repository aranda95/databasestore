'use strict';

angular.module('databasestoreApp').controller('GrifoDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Grifo', 'Producto',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Grifo, Producto) {

        $scope.grifo = entity;
        $scope.productos = Producto.query({filter: 'grifo-is-null'});
        $q.all([$scope.grifo.$promise, $scope.productos.$promise]).then(function() {
            if (!$scope.grifo.producto || !$scope.grifo.producto.id) {
                return $q.reject();
            }
            return Producto.get({id : $scope.grifo.producto.id}).$promise;
        }).then(function(producto) {
            $scope.productos.push(producto);
        });
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
