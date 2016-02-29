'use strict';

describe('Controller Tests', function() {

    describe('Producto Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockProducto, MockCalentador;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockProducto = jasmine.createSpy('MockProducto');
            MockCalentador = jasmine.createSpy('MockCalentador');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Producto': MockProducto,
                'Calentador': MockCalentador
            };
            createController = function() {
                $injector.get('$controller')("ProductoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'databasestoreApp:productoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
