'use strict';

angular.module('databasestoreApp')
    .controller('ProductoController', function ($scope, $state, Producto) {

        $scope.productos = [];
        $scope.loadAll = function() {
            Producto.query(function(result) {
               $scope.productos = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.producto = {
                descripcion: null,
                id: null
            };
        };
    });
