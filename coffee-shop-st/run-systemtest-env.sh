#!/bin/bash
set -euo pipefail

docker stop coffee-shop coffee-shop-db barista &> /dev/null || true

docker run -d --rm \
  --name coffee-shop-db \
  --network dkrnet \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  postgres:9.5

docker run -d --rm \
  --name coffee-shop \
  --network dkrnet \
  -p 8001:9080 \
  sdaschner/coffee-shop:testing-1

docker run -d --rm \
  --name barista \
  --network dkrnet \
  -p 8002:9080 \
  rodolpheche/wiremock:2.6.0 --port 9080
