#!/bin/bash
set -euo pipefail

trap cleanup EXIT

function cleanup() {
  echo stopping Docker containers...
  docker stop coffee-shop coffee-shop-db barista &> /dev/null || true
  rm -Rf /tmp/wad-dropins/*
}



docker run -d --rm \
  --name coffee-shop-db \
  --network dkrnet \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  postgres:9.5

docker run -d --rm \
  --name barista \
  --network dkrnet \
  -p 8002:9080 \
  rodolpheche/wiremock:2.6.0 --port 9080


mkdir -p /tmp/wad-dropins/

docker run -d --rm \
  --name coffee-shop \
  --network dkrnet \
  -p 8001:9080 \
  -v /tmp/wad-dropins/:/opt/wlp/usr/servers/defaultServer/dropins/ \
  sdaschner/coffee-shop:testing-1

pushd ../coffee-shop
  wad.sh
popd
