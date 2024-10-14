docker network create --driver bridge docker_alabtaalnet || true

# docker-compose
cd docker || return
# docker-compose rm -s -f
docker compose down --rmi all --remove-orphans
docker compose up -d --build --force-recreate
# docker stack rm om-stack
# docker stack deploy -c docker-compose.yml om-stack
