#!/bin/bash

set -e

user=$(id -u):$(id -g)
docker-compose run --rm --user $user serialize starter.sh $*