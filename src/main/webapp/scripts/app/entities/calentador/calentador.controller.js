'use strict';

angular.module('databasestoreApp')
    .controller('CalentadorController', function ($scope, $state, Calentador) {

        $scope.calentadors = [];
        $scope.loadAll = function() {
            Calentador.query(function(result) {
               $scope.calentadors = result;
            });
        };
        $scope.loadAll();



        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.calentador = {
                modelo: null,
                gas: null,
                litros: null,
                cantidad: null,
                comentario: null,
                id: null
            };
        };

        $scope.orden = function(x){
            console.log(x);
            $scope.tipo = x;

        };

        $scope.actualizar = function(){
            console.log($scope.search);
        }

        $scope.search ={

        };

    });
