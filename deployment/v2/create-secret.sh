#!/bin/sh

PASSWORD="$(dd if=/dev/urandom bs=1 count=8 status=none | base64 | tr / 0)"

kubectl create secret generic coffee-shop-db-user --from-literal=sql="drop user if exists coffeeshop; create user coffeeshop with password '"${PASSWORD}"';" --from-literal=password="${PASSWORD}"