cd config
mvn clean install
cf push
cd ..

cd rick
mvn clean install
cf push
cd ..

cd meeseeks
mvn clean install
cf push
cd ..

cd mcdonalds
mvn clean install
cf push
cd ..
