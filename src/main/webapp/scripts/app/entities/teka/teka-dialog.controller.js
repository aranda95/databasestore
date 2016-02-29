'use strict';

angular.module('databasestoreApp').controller('TekaDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Teka', 'Producto',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Teka, Producto) {

        $scope.teka = entity;
        $scope.productos = Producto.query({filter: 'teka-is-null'});
        $q.all([$scope.teka.$promise, $scope.productos.$promise]).then(function() {
            if (!$scope.teka.producto || !$scope.teka.producto.id) {
                return $q.reject();
            }
            return Producto.get({id : $scope.teka.producto.id}).$promise;
        }).then(function(producto) {
            $scope.productos.push(producto);
        });
        $scope.load = function(id) {
            Teka.get({id : id}, function(result) {
                $scope.teka = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('databasestoreApp:tekaUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.teka.id != null) {
                Teka.update($scope.teka, onSaveSuccess, onSaveError);
            } else {
                Teka.save($scope.teka, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
