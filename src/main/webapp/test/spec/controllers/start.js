'use strict';

describe('Controller: StartCtrl', function () {

  // load the controller's module
  beforeEach(module('unoApp'));

  var StartCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    StartCtrl = $controller('StartCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(StartCtrl.awesomeThings.length).toBe(3);
  });
});
