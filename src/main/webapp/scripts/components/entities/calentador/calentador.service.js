'use strict';

angular.module('databasestoreApp')
    .factory('Calentador', function ($resource, DateUtils) {
        return $resource('api/calentadors/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
