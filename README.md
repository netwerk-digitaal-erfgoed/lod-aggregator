# LOD-aggregator

## Summary

An increasing number of heritage institutes are taking steps to publish their collection information as Linked Open Data (LOD), especially in Schema.org to increase the visibility in Google and other major search engines. To lower the barriers for the contributing data to Europeana we designed a basic pipeline, the **LOD-aggregator**, that harvests the published Linked Data and converts the Schema.org information to the Europeana Data Model (EDM) to make the ingest in the Europeana harvesting platform possible. This pipeline was build to demonstrate the feasibility for this approach and to prove that production ready data can be provided this way.

See the following specifications for more background information:

* [Specifying a linked data dataset for Europeana and aggregators](https://zenodo.org/record/3817314)
* [Guidelines for providing and handling Schema.org metadata in compliance with Europeana](https://zenodo.org/record/3817236)
  
_This software was developped as part of the Europeana Common Culture project. Main development and testing was done by Europeana R&D and the Dutch Digital Heritage Network (NDE)._

## Installation

Use the following command or your favorite Git tool to clone the repo to your local environment

```bash
git clone https://github.com/netwerk-digitaal-erfgoed/europeana-cc-lod.git
```

Set the defaults values for your local installation using a `.env` file:

```bash
cp env.dist .env
# use your favorite editor to set VAR_PROVIDER to the appropriate value in `.env`
```

The tools expect input files to be in `./data`, shape files to be in `./shapes` and query files to be in `./queries`. Use the environment variables in `.env` to change these defaults.

After cloning the repository only a build of the crawl service is required. Use the following command:

```bash
docker-compose build --no-cache crawl
```

**Important notes:**

* Europeana requires an identification label of the institution that runs the LOD aggregation service. This can be specified in the `.env` file with the VAR_PROVIDER variable or set with the `--provider` parameter during runtime.

* Run the following command **each time** you start a new session:

  ```bash
  source bin/setpath
  ```

  This will add ./bin path to your $PATH so you can run the commands without prefixing them.

No further configuration is needed. See [docker-compose.yml](./docker-compose.yml) and [the starter.sh script](./scripts/starter) for more details on how the crawler and JENA tools (`sparql` and `shacl`) are being called in more detail.

## General workflow

For a generic harvesting process the following tasks should be performed:

1. run the **crawl service** to harvest the data described by a dataset description

2. run the **map service** to convert the harvested data from Schema.org to EDM

3. run the **validate service** to validate the generated EDM data

4. run the **convert service** to prepare the data for ingesting into Europeana

Optional steps:

- run the **craw** and **validate service** to download and check only the dataset description
- run the **serialize service** to transform the RDF from one serialization (N-Triples, RDF/XML, Turtle) into another


### Documented test runs

In order to demonstrate the use of these tools with real world LOD data a number of test cases have been documented. See the [tests directory](./tests) for more information.

## Running the crawler

Run the crawler using the following command:

```bash
crawl.sh --dataset-uri {dataset URI} --output {output filename} [--description_only]
```

To download **only the dataset description** found at the URI of the dataset use the option `--description-only`.

Check the `crawler.log` logfile in the `data` dir for the results of the crawl proces. Both progress and error information can be found here.

## Running the mapper

Run the mapper tool to convert the downloaded Linked Data into an output format using a SPARQL CONSTRUCT query. The default configuration is based on converting data in schema.org format to EDM. The generic sparql query in `schema2edm.rq` takes care of this, see the [queries dir](./queries) for more information.

```bash
map.sh --data {data file} --output {output file} \
  [ --query {query file} ] \
  [ --format {serialization format} ] \
  [ --provider { provider name } ]
```

The default query file used is `schema2edm.rq`. For fixing input data or mapping from other formats to EDM you can provide your own sparql construct query.

The default serialization is `RDF/XML` as this is the preferred format for delivery to Europeana. This can be overruled with `--format <format>` option. See the [starter.sh script](./scripts/starter.sh) for the available serialization formats.

The provider option sets the `edm:provider` property, if not specified it is derived from the VAR_PROVIDER variable set in `.env`.

## Running the validator

Run the validator using the following command:

```bash
validate.sh --data {data file} [ --shape {shape file} ]
```

The default shape file is `shacl_edm.ttl` and checks the data according to the EDM specifications. 

The result of the validation is written to `errors.txt`. 

## Prepare the data for delivery to Europeana 

The Europeana import proces is XML based so the data also needs to comply with certain XML constraints. The following command takes care of this. The result is a zipfile containing seperate records for each resource.

```bash
convert.sh --data {input file} --output {output file`.zip`}
```

Because the output file is a zipfile the extension should be set to `.zip`.

## Converting RDF data into different serializations

For debugging and testing it can be helpful to convert RDF into other serialization formats using this command:

```bash
serialize.sh --data {input file} --format {RDF format} --output {output file}
```

See the [starter.sh](./scripts/starter.sh) script for a full list of `format` options.