'use strict';

angular.module('databasestoreApp').controller('SanitarioDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Sanitario', 'Producto',
        function($scope, $stateParams, $uibModalInstance, entity, Sanitario, Producto) {

        $scope.sanitario = entity;
        $scope.productos = Producto.query();
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
