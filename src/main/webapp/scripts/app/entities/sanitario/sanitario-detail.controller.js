'use strict';

angular.module('databasestoreApp')
    .controller('SanitarioDetailController', function ($scope, $rootScope, $stateParams, entity, Sanitario, Producto) {
        $scope.sanitario = entity;
        $scope.load = function (id) {
            Sanitario.get({id: id}, function(result) {
                $scope.sanitario = result;
            });
        };
        var unsubscribe = $rootScope.$on('databasestoreApp:sanitarioUpdate', function(event, result) {
            $scope.sanitario = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
