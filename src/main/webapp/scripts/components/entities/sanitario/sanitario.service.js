'use strict';

angular.module('databasestoreApp')
    .factory('Sanitario', function ($resource, DateUtils) {
        return $resource('api/sanitarios/:id', {}, {
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
