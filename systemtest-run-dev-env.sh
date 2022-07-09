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
docker build -t coffee-shop:tmp .

docker run -d \
  --name coffee-shop \
  --network dkrnet \
  -e QUARKUS_LAUNCH_DEVMODE=true \
  -p 8001:8080 \
  -p 5005:5005 \
  coffee-shop:tmp \
  java -jar \
  "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005" \
  -Dquarkus.live-reload.password=123 \
  -Dquarkus.http.host=0.0.0.0 \
  /deployments/quarkus-run.jar

# wait for app startup
wget --quiet --tries=30 --waitretry=2 --retry-connrefused -O /dev/null http://localhost:8001/q/health

mvn quarkus:remote-dev -Ddebug=false -Dquarkus.live-reload.url=http://localhost:8001 -Dquarkus.live-reload.password=123 -Dquarkus.package.type=mutable-jar
