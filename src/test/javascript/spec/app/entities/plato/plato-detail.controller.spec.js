'use strict';

describe('Controller Tests', function() {

    describe('Plato Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPlato, MockProducto;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPlato = jasmine.createSpy('MockPlato');
            MockProducto = jasmine.createSpy('MockProducto');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Plato': MockPlato,
                'Producto': MockProducto
            };
            createController = function() {
                $injector.get('$controller')("PlatoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'databasestoreApp:platoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
