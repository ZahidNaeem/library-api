docker network create --driver bridge docker_alabtaalnet || true

MODULE=library
cd ../$MODULE-ui || return
# UI configuration
cp -r build/. ../$MODULE-api/docker/ui/build-js/
cd ..

# API configuration
cd $MODULE-api || return
mvn clean install spring-boot:repackage -X
cd target || return
cp *.jar ../docker/api/$MODULE.jar
cd ..

# docker-compose
cd docker || return
# docker-compose rm -s -f
docker compose down --rmi all --remove-orphans
docker compose up -d --build --force-recreate
# docker stack rm om-stack
# docker stack deploy -c docker-compose.yml om-stack
