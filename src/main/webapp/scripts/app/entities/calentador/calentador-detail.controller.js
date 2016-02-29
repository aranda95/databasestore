'use strict';

angular.module('databasestoreApp')
    .controller('CalentadorDetailController', function ($scope, $rootScope, $stateParams, entity, Calentador, Producto) {
        $scope.calentador = entity;
        $scope.load = function (id) {
            Calentador.get({id: id}, function(result) {
                $scope.calentador = result;
            });
        };
        var unsubscribe = $rootScope.$on('databasestoreApp:calentadorUpdate', function(event, result) {
            $scope.calentador = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
