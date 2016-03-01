'use strict';

angular.module('databasestoreApp')
    .controller('AzulejoDetailController', function ($scope, $rootScope, $stateParams, entity, Azulejo, Producto) {
        $scope.azulejo = entity;
        $scope.load = function (id) {
            Azulejo.get({id: id}, function(result) {
                $scope.azulejo = result;
            });
        };
        var unsubscribe = $rootScope.$on('databasestoreApp:azulejoUpdate', function(event, result) {
            $scope.azulejo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
