#!/usr/bin/env bash

set -a

echo ""
echo "--- Nettoyage de la version précédente de maven..."
mvn clean
echo "--- Nettoyage terminé."
echo ""


echo ""
echo "--- Installation des dépendances..."
cd webuno/
if [ -f "package.json" ]; then
    npm install
else
    echo "\nLe fichier package.json n'existe pas !"
    exit 1;
fi
echo "--- Fin d'installation des dépendances."
echo ""



echo ""
echo "--- Build / Tests en cours..."
if [ -f "Gruntfile.js" ]; then
    grunt --verbose
else
    echo "\nLe fichier Gruntfile.js n'existe pas !"
    exit 1;
fi
echo "--- Build / Tests terminés !"
echo ""



echo ""
echo "--- Suppression de l'ancien build en cours..."
cd ../src/main/
if [ -d "webapp" ]; then
    rm -rf webapp/
    mkdir webapp
fi
echo "--- Suppression de l'ancien build terminée."
echo ""



echo ""
echo "--- Copie du build de dev dans le dossier de production..."
cd ../../
if [ -f "webuno/dist/index.html" ] && [ -d "src/main/webapp" ]; then
    cp -rf webuno/dist/* src/main/webapp/
else
    echo ""
    echo "Fichier(s) manquant(s) dans webuno/dist/ ou dossier src/main/webapp/ introuvable !"
    exit 1;
fi
mkdir src/main/webapp/WEB-INF
if [ -f "webuno/WEB-INF/web.xml" ] && [ -d "src/main/webapp/WEB-INF/" ]; then
    cp -rf webuno/WEB-INF/* src/main/webapp/WEB-INF/
else
    echo ""
    echo "Fichier web.xml manquant dans webuno/WEB-INF/ ou dossier src/main/webapp/WEB-INF introuvable !"
    exit 1;
fi
echo "--- Copie des fichiers terminée."
echo ""



echo ""
echo "--- Packaging du projet..."
if [ -f "pom.xml" ]; then
    mvn package
else
    echo ""
    echo "Le fichier pom.xml n'existe pas !"
    exit 1;
fi
echo "--- Packaging du projet terminé."
echo ""



echo ""
echo "|---------------------------------------|"
echo "|----- MISE EN PRODUCTION TERMINÉE -----|"
echo "|---------------------------------------|"
echo ""