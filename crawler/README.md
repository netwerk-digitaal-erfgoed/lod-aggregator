Crawler
==============================

## Create a shared volume (if none exists)

The volume is used by the applications that make up this project.

    docker volume create --name europeana_cc_lod_share

## Build the container

    docker-compose build --no-cache

## Run the crawler

This script extracts the URIs of resources in a dataset description, crawls the URIs and stores the results into an N-Triples file on the shared volume:

    docker-compose run --rm crawler /bin/bash ./crawler.sh \
        -dataset_uri http://data.bibliotheken.nl/id/dataset/rise-centsprenten \
        -output_file /opt/europeana_cc_lod_share/crawled/centsprenten.nt \
        -log_file run.log

## Login to container (optional)

    docker-compose run --rm crawler /bin/bash
