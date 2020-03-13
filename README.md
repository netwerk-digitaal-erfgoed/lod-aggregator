Crawler
=======

## Build the crawler

`docker-compose build --no-cache crawler`

## Download Dataset Description
Start with downloading the dataset description only.

`docker-compose run --rm --user 1000:1000 crawler /bin/bash ./crawler.sh \
  -dataset_uri http://data.bibliotheken.nl/id/dataset/rise-centsprenten \
  -dataset_description_only \
  -output_file /opt/data/centsprenten_dataset.nt \
  -log_file /opt/crawler.log`

Set --user to prevent to your UID:GID to prevent docker-compose from creating files owned by 'root'

## Validate
Validate the dataset description using SHACL shape constraints (to be done).

## Download all data
Download the complete dataset.

`docker-compose run --rm --user 1000:1000 crawler /bin/bash ./crawler.sh \
    -dataset_uri http://data.bibliotheken.nl/id/dataset/rise-centsprenten \
    -output_file /opt/data/centsenten.nt \
    -log_file /opt/crawler.log`

## Map
Map the orginal dataset to EDM using a 'construct' SPARQL query 

`docker-compose run --rm --user 1000:1000 map starter.sh --data centsprenten.nt --query example_schema2edm.rq --output centsprenten_edm.ttl`

## Validate
Validate the EDM data using SHACL shape constraints

`docker-compose run --rm --user 1000:1000 validate starter.sh --data centsprenten_edm.ttl --shape example_shacl_edm.ttl --output validate_results.ttl`

## Serialize
Convert the EDM data to the XML/RDF format for processing by the Europeana ingest tool

`docker-compose run --rm --user 1000:1000 serialize starter.sh --data centsprenten_edm.ttl --output centsprenten_edm.rdf`

