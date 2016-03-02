'use strict';

angular.module('databasestoreApp').controller('ProductoDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Producto', 'Calentador', 'Teka', 'Sanitario', 'Azulejo', 'Plato', 'Saco', 'Grifo',
        function($scope, $stateParams, $uibModalInstance, entity, Producto, Calentador, Teka, Sanitario, Azulejo, Plato, Saco, Grifo) {

        $scope.producto = entity;
        $scope.calentadors = Calentador.query();
        $scope.tekas = Teka.query();
        $scope.sanitarios = Sanitario.query();
        $scope.azulejos = Azulejo.query();
        $scope.platos = Plato.query();
        $scope.sacos = Saco.query();
        $scope.grifos = Grifo.query();
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
