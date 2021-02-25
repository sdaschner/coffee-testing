#!/bin/bash
set -eu

curl http://localhost:8080/orders -XPOST -i -H 'Content-Type: application/json' -d '{"origin":"Colombia","type":"Espresso"}'
