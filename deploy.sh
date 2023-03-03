./gradlew -Pprod,api-docs -Pswagger -Pwar clean bootWar

docker build -t kks/interpretation:0.1.0 .
 cd /opt/deploy/WHONET-is && docker-compose down && docker-compose up -d && cd -
