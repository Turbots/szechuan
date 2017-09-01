#!/usr/bin/env bash

cd common
mvn clean install
cd ..

cd meeseeks-box
mvn clean install
cf push
cd ..

cd rick
mvn clean install
cf push
cd ..

cd mr-meeseeks
mvn clean install
cf push
cd ..

cd mcdonalds
mvn clean install
cf push
cd ..

cd morty
mvn clean install
cf push
cd ..