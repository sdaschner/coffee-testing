#!/bin/bash
set -euo pipefail

cd ${0%/*}/coffee-shop

docker stop coffee-shop coffee-shop-db barista &> /dev/null || true

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

pushd src/main/docker/
  docker build -t tmp-builder .
popd


docker run --rm -ti \
  --name coffee-shop \
  --network dkrnet \
  -p 8001:8080 \
  -v /home/sebastian/.m2/:/root/.m2/ \
  -v $(pwd)/:/workspace/ \
  tmp-builder /quarkus-dev.sh
