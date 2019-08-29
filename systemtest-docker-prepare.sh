#!/bin/bash
set -euo pipefail
cd ${0%/*}/coffee-shop

mvn clean package

docker build -t coffee-shop .

mkdir -p ./target/liberty/wlp

docker volume rm liberty_dev_vol &> /dev/null || true

docker volume create --driver local \
    --opt type=none \
    --opt device=$(pwd)/target/liberty/wlp/ \
    --opt o=bind \
    liberty_dev_vol
