'use strict';

angular.module('databasestoreApp')
    .controller('SacoController', function ($scope, $state, Saco) {

        $scope.sacos = [];
        $scope.loadAll = function() {
            Saco.query(function(result) {
               $scope.sacos = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.saco = {
                modelo: null,
                cantidad: null,
                comentario: null,
                id: null
            };
        };
    });
