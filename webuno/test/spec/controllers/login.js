'use strict';

describe('Controller: LoginController', function () {

    // load the controller's module
    beforeEach(module('unoApp'));

    var LoginController,
        scope;

    // Initialize the controller and a mock scope
    beforeEach(inject(function ($controller, $rootScope) {
        scope = $rootScope.$new();
        LoginController = $controller('LoginController', {
            $scope: scope
            // place here mocked dependencies
        });
    }));

    it('test de la variable error', function () {
        expect(scope.error).toBeDefined();
    });

    it('test de la variable newUser', function () {
        expect(scope.newUser).toBeDefined();
    });

    it('test de la fonction goLoginGuess', function () {
        expect(scope.goLoginGuess).toBeDefined();
    });

    it('test de la fonction goLogin', function () {
        expect(scope.goLogin).toBeDefined();
    });
});
