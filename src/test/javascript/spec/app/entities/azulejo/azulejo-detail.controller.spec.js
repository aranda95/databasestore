'use strict';

describe('Controller Tests', function() {

    describe('Azulejo Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockAzulejo, MockProducto;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockAzulejo = jasmine.createSpy('MockAzulejo');
            MockProducto = jasmine.createSpy('MockProducto');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Azulejo': MockAzulejo,
                'Producto': MockProducto
            };
            createController = function() {
                $injector.get('$controller')("AzulejoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'databasestoreApp:azulejoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
