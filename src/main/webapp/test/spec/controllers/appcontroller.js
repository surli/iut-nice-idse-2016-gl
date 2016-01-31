'use strict';

describe('Controller: AppcontrollerCtrl', function () {

  // load the controller's module
  beforeEach(module('unoApp'));

  var AppcontrollerCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    AppcontrollerCtrl = $controller('AppcontrollerCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(AppcontrollerCtrl.awesomeThings.length).toBe(3);
  });
});
