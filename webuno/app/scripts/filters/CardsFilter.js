'use strict';

/**
 * Filtre WordToNumber
 * Permet de convertir un nombre à lettres en nombre à chiffres
 *
 * EX: Zero => 0
 *
 */
angular.module('unoApp')
    .filter('WordToNumber', function() {
        var cards = {
            Zero     : 0,
            One      : 1,
            Two      : 2,
            Three    : 3,
            Four     : 4,
            Five     : 5,
            Six      : 6,
            Seven    : 7,
            Eight    : 8,
            Nine     : 9,
            Skip     : 10,
            Reverse  : 11,
            DrawTwo  : 12,
            Wild     : 13,
            DrawFour : 14
        };

        return function (word) {
            return cards[word];
        };
    })
    .filter('FamilyToColor', function () {
        var colors = {
            Yellow: '#ffaa00',
            Blue: '#5555ff',
            Green: '#55aa55',
            Red: '#ff5555'
        };

        return function (color) {
            return colors[color];
        }
    });