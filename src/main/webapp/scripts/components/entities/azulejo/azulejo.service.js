'use strict';

angular.module('databasestoreApp')
    .factory('Azulejo', function ($resource, DateUtils) {
        return $resource('api/azulejos/:id', {}, {
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
