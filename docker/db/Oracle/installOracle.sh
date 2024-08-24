#!/bin/bash

# Variables
IMAGE_NAME="loliconneko/oracle-ee-11g"
CONTAINER_NAME="oracle-11g-ee"
SERVICE_NAME="EE.oracle.docker"
SID="EE"

# create network
docker network create --driver bridge docker_alabtaalnet || true

# Check if Oracle image is already pulled
if docker images --format "{{.Repository}}" | grep -q $IMAGE_NAME; then
  echo "Oracle image already pulled, skipping..."
else
  echo "Pulling Oracle image..."
  docker pull p$IMAGE_NAME
fi

# Check if Oracle container already exists
if docker ps -a --format "{{.Names}}" | grep -q $CONTAINER_NAME; then
  echo "Oracle container already exists, skipping..."
else
  echo "Creating Oracle container..."
  docker run  -d \
              -p 1521:1521 \
              --name $CONTAINER_NAME \
              -v ./scripts/init.sql:/etc/entrypoint-initdb.d/init.sql \
              --net=docker_alabtaalnet \
              --restart always \
              $IMAGE_NAME
fi