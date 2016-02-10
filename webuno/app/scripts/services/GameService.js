'use strict';

angular.module('unoApp')
    .service('Game', function (localStorageService, $http, $q) {
        return {
            getCartes: function () {
                return localStorageService.get('cartes');
            },
            piocherCarte: function () {
                localStorageService.push('cartes',cartes[cartes.length % 8]);
            },
            jouerCarte: function (carte) {
                cartes.splice(cartes.indexOf(carte), 1);
                localStorageService.set('fausse', carte);
            }
        }
            ;
    });