'use strict';

angular.module('databasestoreApp')
    .controller('AzulejoController', function ($scope, $state, Azulejo) {

        $scope.azulejos = [];
        $scope.loadAll = function() {
            Azulejo.query(function(result) {
               $scope.azulejos = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.azulejo = {
                marca: null,
                m2: null,
                comentario: null,
                id: null
            };
        };
    });
