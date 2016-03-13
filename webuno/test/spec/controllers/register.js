'use strict';

describe('Controller: RegisterController', function () {

    // load the controller's module
    beforeEach(module('unoApp'));

    var RegisterController,
        scope;

    // Initialize the controller and a mock scope
    beforeEach(inject(function ($controller, $rootScope) {
        scope = $rootScope.$new();
        RegisterController = $controller('RegisterController', {
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

    it('test de la fonction goRegister', function () {
        expect(scope.goRegister).toBeDefined();
    });
});
