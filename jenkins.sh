#!/usr/bin/env bash

cd webuno/

echo "Build en cours..."
grunt build --force
echo "Build terminé !"

echo "Suppression de l'ancien build en cours..."
cd ../src/main/
rm -rf webapp/
echo "Suppression de l'ancien build terminée."

echo "Copie du build de dev dans le dossier de production..."
cp -rf ../../webuno/dist/ webapp/
cp -rf ../../webuno/WEB-INF/ webapp/WEB-INF/
echo "Copie des fichiers terminée."

echo "***** MISE EN PRODUCTION OPÉRATIONNELLE *****"