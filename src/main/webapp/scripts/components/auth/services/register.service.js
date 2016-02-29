'use strict';

angular.module('databasestoreApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


