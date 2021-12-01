#!/bin/bash
set -eu

curl http://localhost:8001/coffee-shop/coffee/orders -XPOST -i -H 'Content-Type: application/json' -d '{"origin":"Colombia","type":"Espresso"}'
