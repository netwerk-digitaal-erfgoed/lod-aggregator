# LOD-aggregator

## Summary

An increasing number of heritage institutes are taking steps to publish their collection information as Linked Open Data (LOD), especially in Schema.org to increase the visibility in Google and other major search engines. To lower the barriers for the contributing data to Europeana we designed a basic pipeline, the **LOD-aggregator**, that harvests the published Linked Data and converts the Schema.org information to the Europeana Data Model (EDM) to make the ingest in the Europeana harvesting platform possible. This pipeline was build to demonstrate the feasibility for this approach and to prove that production ready data can be provided this way.

_This project was developped in as part of the Europeana Common Culture project. Main development and testing was done by Europeana R&D and the Dutch Digital Heritage Network (NDE)._

## Installation

Use the following command or your favorite Git tool to clone the repo to your local environment

```bash
git clone https://github.com/netwerk-digitaal-erfgoed/europeana-cc-lod.git
```

Next set defaults value for your local installation using a `.env` file:

```bash
cp env.dist .env
# use your favorite editor to set VAR_PROVIDER to the appropriate value in `.env`
```

The tools expect input files to be in `./data`, shape files to be in `./shapes` and query files to be in `./queries`. Environment variables (in `.env`) can be set to override these defaults.

**Important:** Europeana requires an identification label of the institution that runs the LOD aggregation service. This can be specified in the `.env` file with the VAR_PROVIDER variable or set with the `--provider` parameter during runtime.

After cloning the repository only a build of the crawl service is required. Use the following command:

```bash
docker-compose build --no-cache crawl
```

No further configuration is needed. See [docker-compose.yaml](./docker-compose.yaml) and [the starter.sh script](./scripts/starter) for more details on how the crawler and JENA tools (`sparql` and `shacl`) are being called.

**Note:**
All services are run in containers started by the `docker-compose run` command. In order to prevent docker from creating files owned by root on your filesystem specify the `--user UID:GID` option when running the docker-compose command. UID:GID should match your current account settings (in this documentation 1000:1000 is used as an example).

## General workflow

For a generic harvesting process the following steps are being taken:

1. run the **craw service** to harvest the data described by a dataset description

2. run the **map service** to convert the harvested data from Schema.org to EDM

3. run the **validate service** to validate the produced EDM data

Optional steps:

- run the **craw** and **validate service** to download and check only the dataset description
- run the **serialize service** to transform the RDF from one serialization (N-Triples, RDF/XML, Turtle) into another
- zip the datasets for efficient transportation (depending on the tools on your OS)

### Documented test runs

In order to demonstrate the use of these tools with real world LOD data a number of test cases have been documented. See the [test dir](./test) for more information.

## Running the crawler

Run the crawler using the following syntax:

```bash
docker-compose run --rm --user 1000:1000 crawl starter.sh \
  --dataset-uri {dataset URI}\
  --output {output filename}\
  [--description_only]
```

To download **only the dataset description** found at the URI of the dataset use the option `--description-only`.

Check the `crawler.log` logfile in the `data` dir for the results of the crawl proces. Both progress and error information can be found here.

## Running the mapper

Run the mapper tool to convert the downloaded Linked Data into an output format using a SPARQL CONSTRUCT query. The default configuration is based on converting data in schema.org format to EDM. The generic sparql query in `schema2edm.rq` takes care of this, see the [queries dir](./queries) for more information.

```bash
docker-compose run --rm --user 1000:1000 map starter.sh \
  --data {data file} \
  --query {query file} \
  --output {output file} \
  [--provider { provider name } ] \
  [--format {serialization format}]
```

The default serialization is Turtle. For delivery to Europeana `RDF/XML` is prefered, this can be specified through the `--output RDF/XML` option. See the [starter.sh script](./scripts/starter.sh) for the available serialization formats.

## Running the validator

Run the validator using the following command:

```bash
docker-compose run --rm --user 1000:1000 validate starter.sh \
  --data {data file} \
  --shape {shape file} \
  --output {result file}
```

The result file is a formal SHACL validation report written in Turtle and can be found in the `data` dir. See the [shapes dir](./shapes) for more info on the available shape files and tools for testing and debugging shape files.

### Export validation results to CSV

For further processing a CSV containing the results of a validation run can be created with the following command:

```bash
docker-compose run --rm --user 1000:1000 map starter.sh \
  --data {validation report} \
  --query list-errors.rq \
  --format CSV \
  --output errors.csv
```

## Converting RDF data into different serializations

For debugging and testing it can be helpful to convert RDF into other serialization formats using this command:

```bash
docker-compose run --rm --user 1000:1000 serialize starter.sh \
  --data {input file} \
[ --format {RDF format} ] \
  --output {output file}
```

See the [starter.sh](./scripts/starter.sh) script for a full list of `format` options. The default format is Turtle.

## Creating a zip file

Zip the result for transport to Europeana:

```bash
# note the ./data in this command!
gzip ./data/{file name}
```

The result can be found in the `data` dir with the extension `.gz`
