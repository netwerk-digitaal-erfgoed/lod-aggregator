# Using the lod-aggregator tools

## Build the crawler

`docker-compose build --no-cache crawler`

No further configuration is needed. See `docker-compose.yaml` and `./scripts/starter.sh` for more details on how the JENA tools (sparql, shacl, riot) are being called.

The tools (except `crawler` and `gzip` at this moment) expect data to be in `./data`, shape files in `./shapes`, queries in `./queries`. Environment variables (in `.env`) can be set to override these defaults.

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

Before processing the complete data a validation of the dataset description is recommended. One of the SHACL shape files can be used depending on the distribution (list, dump, query) and ontology (VOID, DCAT, Schema.org) used for the dataset. In this example we have a Schema.org description with a list of resources so the shape file to be used is: `shape_dataset_list_schema.ttl`  

```bash
docker-compose run --rm --user 1000:1000 validate starter.sh \
  --data centsprenten_dataset.nt \
  --shape shacl_dataset_list_schema.ttl \
  --output ecc-books-val-ds.ttl
```

## Crawl the full data

Download the complete dataset using the crawler.

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
  --query schema2edm.rq \
  --format RDF/XML \
  --output centsprenten_edm.rdf
```

## Validate the data

Validate the EDM data using SHACL shape constraints

```bash
docker-compose run --rm --user 1000:1000 validate starter.sh \
  --data centsprenten_edm.rdf \
  --shape shacl_edm.ttl \
  --output validate_results.ttl
```

## Zip the result for transport to Europena

```bash
# note the ./data in this command!
gzip ./data/centsprenten_edm.rdf
```
