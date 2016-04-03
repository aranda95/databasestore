'use strict';

angular.module('databasestoreApp')
    .controller('TekaController', function ($scope, $state, Teka) {

        $scope.tekas = [];
        $scope.loadAll = function() {
            Teka.query(function(result) {
               $scope.tekas = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.teka = {
                tipo: null,
                modelo: null,
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
