#!/usr/bin/env bash

ROUGE="\\033[31m"
VERT="\\033[1;32m"
JAUNE="\\033[1;33m"
NORMAL="\\033[0m"

echo " "
if [ $# != 2 ]; then
    echo "$ROUGE [ERR] Nombre de paramètre invalide $NORMAL"
    echo "     database.sh [user] [password]"
    exit 1;
fi

#Vérification de la commande mysql
echo " --- Vérification que la commande 'mysql' existe ... \c";
command -v mysql > /dev/null && { continue ; } || {
    echo "[$ROUGE ERR $NORMAL]"
    echo "$ROUGE     [ERR] Commande mysql not found $NORMAL"
    exit 1 ;
}

echo "[$VERT OK $NORMAL]";

echo " --- Vérification que la connexion à la base ... \c";
#Test de connection à la base de donnée
bases=`mysql -u $1 --password=$2 -e "show databases;" -B -s 2> /dev/null`

if [ -z "$bases" ];then
        echo "[$ROUGE ERR $NORMAL]"
        echo "$ROUGE     [ERR] Identifiant incorrect ou pas de base de données $NORMAL"
        exit 1;
fi

echo "[$VERT OK $NORMAL]"

echo " --- Vérification de l'existance de la database 'uno' ... \c"
bases=`mysql -u $1 --password=$2 -e "SHOW DATABASES LIKE 'uno';" -B -s 2> /dev/null`
if [ -z "$bases" ];then
    echo "[$VERT NON $NORMAL]"
else
    echo "[$JAUNE OUI $NORMAL]"
    echo " --- Suppression de la database 'uno' existante ... \c"
    mysql -u $1 -p$2 -e "DROP DATABASE uno;" -B -s 2> /dev/null
    bases=`mysql -u $1 --password=$2 -e "SHOW DATABASES LIKE 'uno';" -B -s 2> /dev/null`
    if [ -z "$bases" ];then
        echo "[$VERT OK $NORMAL]"
    else
        echo "[$ROUGE ERR $NORMAL]"
        echo "$ROUGE     [ERR] La database uno existe encore $NORMAL"
        exit 1;
    fi
fi
echo " --- Création de la nouvelle database 'uno' ... \c"
bases=`mysql -uroot -proot < script/buildingDb.sql -B -s 2> /dev/null`
bases=`mysql -u $1 --password=$2 -e "SHOW DATABASES LIKE 'uno';" -B -s 2> /dev/null`
if [ -z "$bases" ];then
        echo "[$ROUGE ERR $NORMAL]"
        echo "$ROUGE     [ERR] La database uno n'existe pas $NORMAL"
        exit 1;
fi
echo "[$VERT OK $NORMAL]"
echo ""
echo " |-----------------------------------------|"
echo " |------- BASE DE DONNEES UNO CREER -------|"
echo " |-----------------------------------------|"
echo ""