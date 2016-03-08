#!/usr/bin/env bash

set -a

echo "\n--- Nettoyage de la version précédente de maven..."
mvn clean
echo "--- Nettoyage terminé.\n"



echo "\n--- Installation des dépendances..."
cd webuno/
if [ -f "package.json" ]; then
    npm install
else
    echo "\nLe fichier package.json n'existe pas !"
    exit 1;
fi
echo "--- Fin d'installation des dépendances.\n"



echo "\n--- Build / Tests en cours..."
if [ -f "Gruntfile.js" ]; then
    grunt --force
else
    echo "\nLe fichier Gruntfile.js n'existe pas !"
    exit 1;
fi
echo "--- Build / Tests terminés !\n"



echo "\n--- Suppression de l'ancien build en cours..."
cd ../src/main/
if [ -d "webapp" ]; then
    rm -rf webapp/
    mkdir webapp
fi
echo "--- Suppression de l'ancien build terminée.\n"



echo "\n--- Copie du build de dev dans le dossier de production..."
cd ../../
if [ -f "webuno/dist/index.html" ] && [ -d "src/main/webapp" ]; then
    cp -rf webuno/dist/* src/main/webapp/
else
    echo "\nFichier(s) manquant(s) dans webuno/dist/ ou dossier src/main/webapp/ introuvable !"
    exit 1;
fi
mkdir src/main/webapp/WEB-INF
if [ -f "webuno/WEB-INF/web.xml" ] && [ -d "src/main/webapp/WEB-INF/" ]; then
    cp -rf webuno/WEB-INF/* src/main/webapp/WEB-INF/
else
    echo "\nFichier web.xml manquant dans webuno/WEB-INF/ ou dossier src/main/webapp/WEB-INF introuvable !"
    exit 1;
fi
echo "--- Copie des fichiers terminée.\n"



echo "\n--- Packaging du projet..."
if [ -f "pom.xml" ]; then
    mvn package
else
    echo "\nLe fichier pom.xml n'existe pas !"
    exit 1;
fi
echo "--- Packaging du projet terminé.\n"



echo "\n|---------------------------------------|"
echo "|----- MISE EN PRODUCTION TERMINÉE -----|"
echo "|---------------------------------------|\n"