'use strict';

angular.module('databasestoreApp')
    .factory('Teka', function ($resource, DateUtils) {
        return $resource('api/tekas/:id', {}, {
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
