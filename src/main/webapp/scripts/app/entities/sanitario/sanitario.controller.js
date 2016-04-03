'use strict';

angular.module('databasestoreApp')
    .controller('SanitarioController', function ($scope, $state, Sanitario) {

        $scope.sanitarios = [];
        $scope.loadAll = function() {
            Sanitario.query(function(result) {
               $scope.sanitarios = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.sanitario = {
                modelo: null,
                salida: null,
                medidas: null,
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
