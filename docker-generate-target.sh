docker network create --driver bridge docker_alabtaalnet || true

MODULE=library
cd ../$MODULE-ui || return
# UI configuration
if [ ! -d ./node_modules ]; then
    npm install
fi
npm run build
rm -rf ../$MODULE-api/docker/ui/build-js
cp -r build/. ../$MODULE-api/docker/ui/build-js/
cd ..

# API configuration
cd $MODULE-api || return
mvn clean install spring-boot:repackage -X
rm -f ./docker/api/$MODULE.jar
cd target || return
cp *.jar ../docker/api/$MODULE.jar
cd ..

# docker-compose
cd docker || return
# docker-compose rm -s -f
docker compose down --rmi all
docker compose up -d --build --force-recreate
# docker stack rm om-stack
# docker stack deploy -c docker-compose.yml om-stack
