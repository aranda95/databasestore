'use strict';

describe('Controller Tests', function() {

    describe('Grifo Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockGrifo, MockProducto;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockGrifo = jasmine.createSpy('MockGrifo');
            MockProducto = jasmine.createSpy('MockProducto');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Grifo': MockGrifo,
                'Producto': MockProducto
            };
            createController = function() {
                $injector.get('$controller')("GrifoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'databasestoreApp:grifoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
