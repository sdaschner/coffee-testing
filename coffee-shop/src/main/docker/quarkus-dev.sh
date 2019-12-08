#!/bin/bash
set -euo pipefail

trap cleanup EXIT

function cleanup() {
  # target directory is written with root user, thus we remove it to prevent file permission issues
  rm -rf /workspace/target/
}

cd /workspace/
mvn compile quarkus:dev
