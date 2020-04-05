# Using the lod-aggregator tools

## Build the crawler

`docker-compose build --no-cache crawler`

No further configuration is needed. See `docker-compose.yaml` and `./scripts/starter.sh` for more details on how the JENA tools (sparql, shacl, riot) are being called.

The tools (except `crawler` and `gzip` at this moment) expect data to be in `./data`, shape files in `./shapes`, queries in `./queries`. Environment variables (in `.env`) can be set to override these defaults.

## Crawl dataset description

Start with downloading the dataset description only

```bash
docker-compose run --rm --user 1000:1000 crawl starter.sh \
  --dataset-uri http://data.bibliotheken.nl/id/dataset/rise-centsprenten \
  --description-only \
  --output centsprenten-dataset.nt
```

## Validate dataset description

Before processing the complete data we validated the dataset description. We used a SHACL shape file defined for datasets with URI lists using the Schema.org ontology:

```bash
docker-compose run --rm --user 1000:1000 validate starter.sh \
  --data centsprenten-dataset.nt \
  --shape shacl_dataset_list_schema.ttl \
  --output centsprenten-dataset-val-ds.ttl
```

The validation fails because the dataset description has no schema:name property which a required according to [the specification](https://docs.google.com/document/d/1ffQt8LyHuldWMbFr79HEZ-_vQUVpcNqaCOAqzN12ycg).

## Crawl the full data

But the complete dataset could be downloaded using the crawler.

```bash
docker-compose run --rm --user 1000:1000 crawl starter.sh \
  --dataset-uri http://data.bibliotheken.nl/id/dataset/rise-centsprenten \
  --output centsprenten.nt
```

Because the dataset and the dataset description are in seperate files we copy merge them into one file:

```bash
cat ./data/centsprenten-dataset.nt ./data/centsprenten.nt > ./data/centsprenten-total.nt
```

## Map the data

Map the crawled data to EDM using a 'construct' SPARQL query

```bash
docker-compose run --rm --user 1000:1000 map starter.sh \
  --data centsprenten-total.nt \
  --query schema2edm.rq \
  --format RDF/XML \
  --output centsprenten-edm.rdf
```

Note: in `.env` VAR_PROVIDER was set to 'KB'.

## Validate the data

Validate the EDM data using SHACL shape constraints

```bash
docker-compose run --rm --user 1000:1000 validate starter.sh \
  --data centsprenten-edm.rdf \
  --shape shacl_edm.ttl \
  --output centsprenten-edm-val.ttl
```

## Zip the result for transport to Europena

```bash
# note the ./data in this command!
gzip ./data/centsprenten-edm.rdf
```
