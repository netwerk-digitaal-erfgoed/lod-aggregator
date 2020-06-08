# Test run with the ECC paintings dataset

Canonical URL of the dataset: <http://data.bibliotheken.nl/id/dataset/rise-centsprenten>

## Downloading the dataset using the crawler tool

Start with downloading the dataset description only

```bash
docker-compose run --rm --user 1000:1000 crawl starter.sh \
  --dataset-uri http://data.bibliotheken.nl/id/dataset/rise-centsprenten \
  --description-only \
  --output centsprenten-dataset.nt
```

## Validating the dataset description

Before processing the complete data we validated the dataset description. We used a SHACL shape file defined for datasets with URI lists using the Schema.org ontology:

```bash
docker-compose run --rm --user 1000:1000 validate starter.sh \
  --data centsprenten-dataset.nt \
  --shape shacl_dataset_list_schema.ttl \
  --output centsprenten-dataset-val-ds.ttl
```

The validation fails because the dataset description has no `schema:name` property which a required according to [the specification](https://docs.google.com/document/d/1ffQt8LyHuldWMbFr79HEZ-_vQUVpcNqaCOAqzN12ycg). This in not a blocking issue, it is possible to continue with the full download.

## Downloading the full data

```bash
docker-compose run --rm --user 1000:1000 crawl starter.sh \
  --dataset-uri http://data.bibliotheken.nl/id/dataset/rise-centsprenten \
  --output centsprenten.nt
```

## Mapping the schema.org data to EDM data

Next step was doing the actual conversion of the resources.

```bash
docker-compose run --rm --user 1000:1000 map starter.sh \
  --data centsprenten.nt \
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

The validation resulted in no errors.

## Zip the result for transport to Europeana

```bash
# note the ./data in this command!
gzip ./data/centsprenten-edm.rdf
```
