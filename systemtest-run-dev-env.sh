#!/bin/bash
set -euo pipefail

cd ${0%/*}/coffee-shop

trap cleanup EXIT

function cleanup() {
  echo stopping containers
  docker stop coffee-shop coffee-shop-db barista &> /dev/null || true
}


cleanup

docker run -d --rm \
  --name coffee-shop-db \
  --network dkrnet \
  -p 5432:5432 \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  postgres:9.5

docker run -d --rm \
  --name barista \
  --network dkrnet \
  -p 8002:8080 \
  rodolpheche/wiremock:2.6.0


# coffee-shop
mvn clean package -Dquarkus.package.type=mutable-jar
docker build -f Dockerfile.dev -t tmp-builder .

docker run -d --rm \
  --name coffee-shop \
  --network dkrnet \
  -p 8001:8080 \
  -p 5005:5005 \
  tmp-builder

# wait for app startup
while [[ "$(curl -s -o /dev/null -w ''%{http_code}'' http://localhost:8001/q/health)" != "200" ]]; do
  sleep 2;
done

mvn quarkus:remote-dev -Ddebug=false -Dquarkus.live-reload.url=http://localhost:8001 -Dquarkus.live-reload.password=123 -Dquarkus.package.type=mutable-jar
