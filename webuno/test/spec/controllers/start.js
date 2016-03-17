/**
 * Created by Teva on 17/03/2016.
 */

'use strict';

describe('Controller: StartController', function () {

  // load the controller's module
  beforeEach(module('unoApp'));

  var StartController,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    StartController = $controller('StartController', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('test de la fonction goGame', function () {
    expect(scope.goGame).toBeDefined();
  });

  it('test de la variable error', function () {
    expect(scope.error).toBeDefined();
  });

  it('test de la variable game', function () {
    expect(scope.game).toBeDefined();
  });

  it('test de la variable user.name qui est le nom user connecté', function () {
    expect(scope.user.name).toBeDefined();
  });

});
