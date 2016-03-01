'use strict';

angular.module('databasestoreApp')
    .controller('SacoDetailController', function ($scope, $rootScope, $stateParams, entity, Saco, Producto) {
        $scope.saco = entity;
        $scope.load = function (id) {
            Saco.get({id: id}, function(result) {
                $scope.saco = result;
            });
        };
        var unsubscribe = $rootScope.$on('databasestoreApp:sacoUpdate', function(event, result) {
            $scope.saco = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
