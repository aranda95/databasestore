'use strict';

angular.module('databasestoreApp').controller('SacoDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Saco', 'Producto',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Saco, Producto) {

        $scope.saco = entity;
        $scope.productos = Producto.query({filter: 'saco-is-null'});
        $q.all([$scope.saco.$promise, $scope.productos.$promise]).then(function() {
            if (!$scope.saco.producto || !$scope.saco.producto.id) {
                return $q.reject();
            }
            return Producto.get({id : $scope.saco.producto.id}).$promise;
        }).then(function(producto) {
            $scope.productos.push(producto);
        });
        $scope.load = function(id) {
            Saco.get({id : id}, function(result) {
                $scope.saco = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('databasestoreApp:sacoUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.saco.id != null) {
                Saco.update($scope.saco, onSaveSuccess, onSaveError);
            } else {
                Saco.save($scope.saco, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
