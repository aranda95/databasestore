'use strict';

angular.module('databasestoreApp')
    .controller('PlatoDetailController', function ($scope, $rootScope, $stateParams, entity, Plato, Producto) {
        $scope.plato = entity;
        $scope.load = function (id) {
            Plato.get({id: id}, function(result) {
                $scope.plato = result;
            });
        };
        var unsubscribe = $rootScope.$on('databasestoreApp:platoUpdate', function(event, result) {
            $scope.plato = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
