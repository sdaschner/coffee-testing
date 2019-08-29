#!/bin/bash
set -euo pipefail
cd ${0%/*}/coffee-shop

sudo chmod -R 777 ./target/liberty/wlp

mvn liberty:dev -DskipServer
