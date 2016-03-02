'use strict';

angular.module('databasestoreApp')
    .controller('ProductoDetailController', function ($scope, $rootScope, $stateParams, entity, Producto, Calentador, Teka, Sanitario, Azulejo, Plato, Saco, Grifo) {
        $scope.producto = entity;
        $scope.load = function (id) {
            Producto.get({id: id}, function(result) {
                $scope.producto = result;
            });
        };
        var unsubscribe = $rootScope.$on('databasestoreApp:productoUpdate', function(event, result) {
            $scope.producto = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
