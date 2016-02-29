'use strict';

angular.module('databasestoreApp')
    .controller('TekaDetailController', function ($scope, $rootScope, $stateParams, entity, Teka, Producto) {
        $scope.teka = entity;
        $scope.load = function (id) {
            Teka.get({id: id}, function(result) {
                $scope.teka = result;
            });
        };
        var unsubscribe = $rootScope.$on('databasestoreApp:tekaUpdate', function(event, result) {
            $scope.teka = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
