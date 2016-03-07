'use strict';

angular.module('databasestoreApp')
    .controller('GrifoController', function ($scope, $state, Grifo) {

        $scope.grifos = [];
        $scope.loadAll = function() {
            Grifo.query(function(result) {
               $scope.grifos = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.grifo = {
                Tipo: null,
                Referencia: null,
                cantidad: null,
                comentario: null,
                id: null
            };
        };
    });
