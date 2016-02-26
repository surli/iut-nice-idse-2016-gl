#!/usr/bin/env bash

cd webuno/

echo "\n--- Installation des dépendances..."
npm install
echo "--- Fin d'installation des dépendances.\n"

echo "\n--- Build / Tests en cours..."
grunt
echo "--- Build / Tests terminés !\n"

echo "\n--- Suppression de l'ancien build en cours..."
cd ../src/main/
rm -rf webapp/
echo "--- Suppression de l'ancien build terminée.\n"

echo "\n--- Copie du build de dev dans le dossier de production..."
cp -rf ../../webuno/dist/ webapp/
cp -rf ../../webuno/WEB-INF/ webapp/WEB-INF/
echo "--- Copie des fichiers terminée.\n"

echo "\n--- Packaging du projet..."
cd ../../
mvn package
echo "--- Packaging du projet terminé.\n"

echo "\n***** MISE EN PRODUCTION OPÉRATIONNELLE *****\n"