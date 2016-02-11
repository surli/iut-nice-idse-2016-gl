'use strict';

// Ajouter le script dans index.html en dessous de <script src="scripts/services/AuthService.js"></script>

angular.module('unoApp')
  .service('Game', function (Auth, $http, $q) {
    // Auth : C'est le fichier AuthService, on l'utilise pour récupérer les informations de l'utilisateur
    // $http : Va nous permettre de lancer des requetes http
    // $q : Va nous permettre de faire des promises
    return {
      // Exemple de route createGame qui prend en parametre un nom de game et un nombre de players
      createGame: function (game, nbPlayers) {

        // on créer et instancie la promise
        var deferred = $q.defer();

        // $http.post prend 3 parametres, dont 2 toujours obligatoire (il me semble) :
        // 1 - (OBLIGATOIRE) l'url sur laquelle faire la requete
        // 2 - (OBLIGATOIRE) le json des données qui vont etre envoyées (si c'etait un GET nous n'aurions pas ce parametre la)
        // 3 - (OPTIONNEL) un tableau d'options en plus, dans notre cas, on envoi dans le header le token de l'utilisateur
        $http.post('/rest/game', {
            // on envoi le nom du game passé en parametre
            game: game,
            // on envoi le nom du player récupéré avec le service Auth
            player: Auth.getUser().name,
            // on envoi le nombre de players passé en parametre
            numberplayers: nbPlayers
          }, {
            // L'API REST demande un token dans le header
            headers: {
              // Donc dans le header on ajoute le token venant de l'utilisateur
              token: Auth.getUser().token
            }
          })
          // debut de la requete asynchrone
          .then(function (response) {
            // on affiche dans la console la reponse de la requete qui est reussie (response) pour etre sure
            console.log(response);

            // vu que la requete est reussie, on place la reponse dans le resolve de la promise pour qu'elle puisse etre retournée
            deferred.resolve(response);
          }, function (error) {
            // on affiche dans la console la reponse de la requete qui est raté (error) pour etre sure
            console.log(error);

            // vu que la requete est rejecter, on renvoi la requete avec un reject dans la promise
            deferred.reject(error);
          });

        // on retourne la promise avec le resolve ou le reject
        return deferred.promise;
      },

      // renvoie les data de la partie
      getGame: function (gameName) {
        var deferred = $q.defer();
        $http.get('/rest/game/' + gameName)
          .then(function (response) {
            console.log(response);
            deferred.resolve(response);
          }, function (error) {
            console.log(error);
            deferred.reject(error);
          });
        return deferred.promise;
      },

        // renvoie toutes les parties en cours
        getAllGames: function () {
            var deferred = $q.defer();
            $http.get('/rest/game')
                .then(function (response) {
                    console.log(response);
                    deferred.resolve(response);
                }, function (error) {
                    console.log(error);
                    deferred.reject(error);
                });
            return deferred.promise;
        },

      // renvoie la main d'un joueur

      getUserHand: function (gameName, userName) {
        var deferred = $q.defer();
        $http.get('/rest/game/' + gameName + "/" + userName)
          .then(function (response) {
            console.log(response);
            deferred.resolve(response);
          }, function (error) {
            console.log(error);
            deferred.reject(error);
          });
        return deferred.promise;
      }

      // Les autres fonctions de ce service se trouverons ici
      // ne pas oublier de mettre une virgule apres l'acolade de chaque fonction (car en réalité le service est un objet de fonctions)

    };
  });
