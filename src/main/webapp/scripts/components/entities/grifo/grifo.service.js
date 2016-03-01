'use strict';

angular.module('databasestoreApp')
    .factory('Grifo', function ($resource, DateUtils) {
        return $resource('api/grifos/:id', {}, {
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
