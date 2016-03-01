'use strict';

angular.module('databasestoreApp')
    .controller('GrifoDetailController', function ($scope, $rootScope, $stateParams, entity, Grifo, Producto) {
        $scope.grifo = entity;
        $scope.load = function (id) {
            Grifo.get({id: id}, function(result) {
                $scope.grifo = result;
            });
        };
        var unsubscribe = $rootScope.$on('databasestoreApp:grifoUpdate', function(event, result) {
            $scope.grifo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
