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
});
