'use strict';

angular.module('databasestoreApp')
    .controller('PlatoController', function ($scope, $state, Plato) {

        $scope.platos = [];
        $scope.loadAll = function() {
            Plato.query(function(result) {
               $scope.platos = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.plato = {
                marca: null,
                color: null,
                medidas: null,
                comentario: null,
                cantidad: null,
                id: null
            };
        };
    });
