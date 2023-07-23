echo "$PWD"
echo "config-server jar file is being created.."
cd ./../../config-server
echo "$PWD"
./jar-builder.sh
cd ./../docker/config-server
echo "$PWD"
echo "docker compose file is being run for config-server.."
docker compose -f docker-compose.yml up -d
