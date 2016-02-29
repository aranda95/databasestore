 'use strict';

angular.module('databasestoreApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-databasestoreApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-databasestoreApp-params')});
                }
                return response;
            }
        };
    });
