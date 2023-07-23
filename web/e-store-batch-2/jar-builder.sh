echo "$PWD"
echo ".jar files are being created..."

echo "api-gateway jar file is being created.."
cd ./api-gateway
echo "$PWD"
./jar-builder.sh

echo "eureka-service jar file is being created.."
cd ../eureka-service
echo "$PWD"
./jar-builder.sh

echo "order-service jar file is being created.."
cd ../order-service
echo "$PWD"
./jar-builder.sh

echo "payment-service jar file is being created.."
cd ../payment-service
echo "$PWD"
./jar-builder.sh

echo "user-service jar file is being created.."
cd ../user-service
echo "$PWD"
./jar-builder.sh

echo ".jar files have been created successfully."
cd ..

echo "$PWD"
echo "docker compose file is being run.."
docker compose -f docker-compose-with-docker-file.yml up -d
