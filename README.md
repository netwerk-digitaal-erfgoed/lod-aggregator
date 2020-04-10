# Using the lod-aggregator tools

## Installation

Afther cloning the repository only a build of the crawl service is required. Use the following command: 

`docker-compose build --no-cache crawl`

Europeana requires and identification of the institution that runs the LOD aggregation service. This can be specified in the `.env` file:

```bash
cp env.dist .env
# use your favorite editor to set VAR_PROVIDER to the appropriate value in `.env`
```

No further configuration is needed. See [docker-compose.yaml](./docker-compose.yaml) and [the starter.sh script](./scripts/starter) for more details on how the crawler and JENA tools (sparql, shacl, riot) are being called.

All services are run in containers started by the `docker-compose run` command. In order to prevent docker from creating files owned by root on your filesystem specify the `--user UID:GID` option when running the docker-compose command. UID:GID should match your current account settings (in this documentation 1000:1000 is used as an example).

The tools expect input files to be in `./data`, shape files to be in `./shapes` and query files to be in `./queries`. Environment variables (in `.env`) can be set to override these defaults.

## Crawler

Run the crawler using the following syntax:

```bash
docker-compose run --rm --user 1000:1000 crawl starter.sh \
  --dataset-uri {dataset URI}\
  --output {ouput filename}\
  [--description_only]
```

To download **only the dataset description** found at the URI of the dataset use the option `--description-only`.

Check the `crawler.log` logfile in the `data` dir for the results of the crawl proces. Both progress and error information can be found here.

## Mapper

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

## Validator

Run the validator using the following command:

```bash
docker-compose run --rm --user 1000:1000 validate starter.sh \
  --data {data file} \
  --shape {shape file} \
  --output {result file}
```

The result file is an formal SHACL validation report written in Turtle and can be found in the `data` dir. See the [shapes dir](./shapes) for more info on the available shape files and tools for testing and debugging shape files.

## Zip

Zip the result for transport to Europena:

```bash
# note the ./data in this command!
gzip ./data/{file name}
```
The result can be found in the `data` dir with the extension `.gz`
