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
    expect(scope.error).toBeUndefined();
  });

  it('test de la variable game', function () {
    expect(scope.game).toBeUndefined();
  });

  // il faut simuler un serveur web et faire passer des paramètre dont user name  et game
  //it('test de la variable user.name qui est le nom user connecté', function () {
  //  expect(scope.user.name).toBeDefined();
  //});
  //
  //it('test de la variable user.game.length, on verifie si le nom de la partie et supérieur à 3 on force un resultat juste en passant la variable à 4', function () {
  //  scope.user.game.length = 'test';
  //  expect(scope.user.game.length).toBeGreaterThan(3);
  //});

  //it('test de la valeur data.status on teste si elle est bien egal à 200', function () {
  //  expect(data.status).toEqual(200);
  //});

});
