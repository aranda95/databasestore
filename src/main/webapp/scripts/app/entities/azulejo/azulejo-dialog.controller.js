'use strict';

angular.module('databasestoreApp').controller('AzulejoDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Azulejo', 'Producto',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Azulejo, Producto) {

        $scope.azulejo = entity;
        $scope.productos = Producto.query({filter: 'azulejo-is-null'});
        $q.all([$scope.azulejo.$promise, $scope.productos.$promise]).then(function() {
            if (!$scope.azulejo.producto || !$scope.azulejo.producto.id) {
                return $q.reject();
            }
            return Producto.get({id : $scope.azulejo.producto.id}).$promise;
        }).then(function(producto) {
            $scope.productos.push(producto);
        });
        $scope.load = function(id) {
            Azulejo.get({id : id}, function(result) {
                $scope.azulejo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('databasestoreApp:azulejoUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.azulejo.id != null) {
                Azulejo.update($scope.azulejo, onSaveSuccess, onSaveError);
            } else {
                Azulejo.save($scope.azulejo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
