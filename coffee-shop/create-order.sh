#!/bin/bash
set -eu

curl http://coffee-shop.kubernetes.local/coffee-shop/resources/orders -XPOST -i -H 'Content-Type: application/json' -d '{"origin":"Colombia","type":"espresso"}'
