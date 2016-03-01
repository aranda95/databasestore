'use strict';

angular.module('databasestoreApp').controller('SanitarioDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Sanitario', 'Producto',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Sanitario, Producto) {

        $scope.sanitario = entity;
        $scope.productos = Producto.query({filter: 'sanitario-is-null'});
        $q.all([$scope.sanitario.$promise, $scope.productos.$promise]).then(function() {
            if (!$scope.sanitario.producto || !$scope.sanitario.producto.id) {
                return $q.reject();
            }
            return Producto.get({id : $scope.sanitario.producto.id}).$promise;
        }).then(function(producto) {
            $scope.productos.push(producto);
        });
        $scope.load = function(id) {
            Sanitario.get({id : id}, function(result) {
                $scope.sanitario = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('databasestoreApp:sanitarioUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.sanitario.id != null) {
                Sanitario.update($scope.sanitario, onSaveSuccess, onSaveError);
            } else {
                Sanitario.save($scope.sanitario, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
