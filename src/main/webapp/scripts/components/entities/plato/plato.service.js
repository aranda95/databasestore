'use strict';

angular.module('databasestoreApp')
    .factory('Plato', function ($resource, DateUtils) {
        return $resource('api/platos/:id', {}, {
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
