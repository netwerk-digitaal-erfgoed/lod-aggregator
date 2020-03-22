# Using the lod-aggregator tools

## Build the crawler

`docker-compose build --no-cache crawler`

No further configuration is needed. See `docker-compose.yaml` and `./scripts/starter.sh` for more details on how the JENA tools (sparql, shacl, riot) are being called.

Tools expect data to be in `./data`, shape files in `./shapes`, queries in `./queries`. Environment variables (in `.env`) can be set to override these defaults.

## Crawl dataset description

Start with downloading the dataset description only

```bash
docker-compose run --rm --user 1000:1000 crawler /bin/bash ./crawler.sh \
  -dataset_uri http://data.bibliotheken.nl/id/dataset/rise-centsprenten \
  -dataset_description_only \
  -output_file /opt/data/centsprenten_dataset.nt \
  -log_file /opt/crawler.log
```

Set `--user` to your UID:GID to prevent docker-compose from creating files owned by 'root'.

## Validate dataset description

Validate the dataset description using SHACL shape constraints (to be done)

## Crawl the full data

Crawl the complete dataset

```bash
docker-compose run --rm --user 1000:1000 crawler /bin/bash ./crawler.sh \
    -dataset_uri http://data.bibliotheken.nl/id/dataset/rise-centsprenten \
    -output_file /opt/data/centsprenten.nt \
    -log_file /opt/crawler.log
```

## Map the data

Map the crawled data to EDM using a 'construct' SPARQL query

```bash
docker-compose run --rm --user 1000:1000 map starter.sh \
  --data centsprenten.nt \
  --query example_schema2edm.rq \
  --output centsprenten_edm.ttl
```

## Validate the data

Validate the EDM data using SHACL shape constraints

```bash
docker-compose run --rm --user 1000:1000 validate starter.sh \
  --data centsprenten_edm.ttl \
  --shape shacl_cho_edm.ttl \
  --output validate_results.ttl
```

## Serialize the data

Convert the EDM data to XML/RDF format for processing by the Europeana ingest tool

```bash
docker-compose run --rm --user 1000:1000 serialize starter.sh \
  --data centsprenten_edm.ttl \
  --format RDF/XML \
  --output centsprenten_edm.rdf
```
