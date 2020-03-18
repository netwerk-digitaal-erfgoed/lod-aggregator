# Configuration

## Build the crawler

`docker-compose build --no-cache crawler`

No further configuration is needed. See `docker-compose.yaml` and `./scripts/starter.sh` for more details on how the JENA tools (sparql, shacl, riot) are being called.

Tools expect data to be in `./data`, shape files in `./shapes`, queries in `./queries`. Environment variables (in `.env`) can be set to override these defaults.

## Crawl Dataset Description
Start with downloading the dataset description only

```
docker-compose run --rm --user 1000:1000 crawler /bin/bash ./crawler.sh \
  -dataset_uri http://data.bibliotheken.nl/id/dataset/rise-centsprenten \
  -dataset_description_only \
  -output_file /opt/data/centsprenten_dataset.nt \
  -log_file /opt/crawler.log
```

Set `--user` to your UID:GID to prevent docker-compose from creating files owned by 'root'.

## Validate
Validate the dataset description using SHACL shape constraints (to be done)

## Crawl
Crawl the complete dataset

```
docker-compose run --rm --user 1000:1000 crawler /bin/bash ./crawler.sh \
    -dataset_uri http://data.bibliotheken.nl/id/dataset/rise-centsprenten \
    -output_file /opt/data/centsprenten.nt \
    -log_file /opt/crawler.log
```

## Map
Map the crawled data to EDM using a 'construct' SPARQL query

```
docker-compose run --rm --user 1000:1000 map starter.sh \
  --data centsprenten.nt \
  --query example_schema2edm.rq \
  --output centsprenten_edm.ttl
```

## Validate
Validate the EDM data using SHACL shape constraints

`docker-compose run --rm --user 1000:1000 validate starter.sh --data centsprenten_edm.ttl --shape CHO_edm.shacl --output validate_results.ttl`

## Serialize
Convert the EDM data to XML/RDF format for processing by the Europeana ingest tool

```
docker-compose run --rm --user 1000:1000 serialize starter.sh \
  --data centsprenten_edm.ttl \
  --output centsprenten_edm.rdf
```
