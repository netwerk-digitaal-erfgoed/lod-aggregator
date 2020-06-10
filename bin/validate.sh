#!/bin/bash

set -e

user=$(id -u):$(id -g)
docker-compose run --rm --user $user validate starter.sh --output temp.ttl $*

docker-compose run --rm --user 1000:1000 map starter.sh --data temp.ttl --query list-errors.rq --format CSV --output errors.txt