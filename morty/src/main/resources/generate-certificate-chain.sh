#!/bin/bash

rm *.jks 2> /dev/null
rm *.pem 2> /dev/null

echo "===================================================="
echo "Creating fake third-party chain root -> ca"
echo "===================================================="

# generate private keys (for root and ca)

keytool -genkeypair -alias root -dname cn=root -validity 10000 -keyalg RSA -keysize 2048 -ext bc:c -keystore root.jks -keypass password -storepass password
keytool -genkeypair -alias ca -dname cn=ca -validity 10000 -keyalg RSA -keysize 2048 -ext bc:c -keystore ca.jks -keypass password -storepass password

# generate root certificate

keytool -exportcert -rfc -keystore root.jks -alias root -storepass password > root.pem

# generate a certificate for ca signed by root (root -> ca)

keytool -keystore ca.jks -storepass password -certreq -alias ca \
| keytool -keystore root.jks -storepass password -gencert -alias root -ext bc=0 -ext san=dns:ca -rfc > ca.pem

# import ca cert chain into ca.jks

keytool -keystore ca.jks -storepass password -importcert -trustcacerts -noprompt -alias root -file root.pem
keytool -keystore ca.jks -storepass password -importcert -alias ca -file ca.pem

echo  "===================================================================="
echo  "Fake third-party chain generated. Now generating my-keystore.jks ..."
echo  "===================================================================="

# generate private keys (for server)

keytool -genkeypair -alias server -dname cn=server -validity 10000 -keyalg RSA -keysize 2048 -keystore my-keystore.jks -keypass password -storepass password

# generate a certificate for server signed by ca (root -> ca -> server)

keytool -keystore my-keystore.jks -storepass password -certreq -alias server \
| keytool -keystore ca.jks -storepass password -gencert -alias ca -ext ku:c=dig,keyEnc -ext san=dns:localhost -ext eku=sa,ca -rfc > server.pem

# import server cert chain into my-keystore.jks

keytool -keystore my-keystore.jks -storepass password -importcert -trustcacerts -noprompt -alias root -file root.pem
keytool -keystore my-keystore.jks -storepass password -importcert -alias ca -file ca.pem
keytool -keystore my-keystore.jks -storepass password -importcert -alias server -file server.pem

echo "================================================="
echo "Keystore generated. Now generating truststore ..."
echo "================================================="

# import server cert chain into my-truststore.jks

keytool -keystore my-truststore.jks -storepass password -importcert -trustcacerts -noprompt -alias root -file root.pem
keytool -keystore my-truststore.jks -storepass password -importcert -alias ca -file ca.pem
keytool -keystore my-truststore.jks -storepass password -importcert -alias server -file server.pem