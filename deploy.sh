./gradlew -Pprod,api-docs -Pswagger -Pwar clean bootWar

docker build -t kks/interpretation:0.1.0 .

docker save -o deploy/int.1.0.tar kks/interpretation:0.1.0

scp deploy/int.1.0.tar root@kks:/tmp/

rm deploy/int.1.0.tar
