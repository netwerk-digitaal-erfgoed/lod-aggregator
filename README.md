# Using the lod-aggregator tools

## Build the crawler

`docker-compose build --no-cache crawl`

No further configuration is needed. See `docker-compose.yaml` and `./scripts/starter.sh` for more details on how the crawler and JENA tools (sparql, shacl, riot) are being called.

The tools expect data to be in `./data`, shape files in `./shapes`, queries in `./queries`. Environment variables (in `.env`) can be set to override these defaults.

## Crawler

Run the crawler using the following syntax:

```bash
docker-compose run --rm --user 1000:1000 crawl starter.sh \
  --dataset-uri {dataset URI}\
  --output {ouput filename}\
  [--description_only]
```

Set `--user` to your UID:GID to prevent docker-compose from creating files owned by 'root'. 

To download **only the dataset description** found at the URI of the dataset use the option `--description-only`.

Check the `crawler.log` logfile for the results of the crawl proces. Both progress and error information can be found here.

## Mapper

Run the mapper tool to convert the downloaded Linked Data into an output format using a SPARQL CONSTRUCT query. The default configuration is based on converting data in schema.org format to EDM. See the `schema2edm.rq` query in the queries dir for more details.

```bash
docker-compose run --rm --user 1000:1000 map starter.sh \
  --data {data file} \
  --query {query file} \
  --output {output file} \
  [--provider { provider name } ] \
  [--format {serialization format}]
```

The default serialization is Turtle. For delivery to Europeana `RDF/XML` is prefered, this can be specified through the `--output RDF/XML` option. See the `starter.sh` script in the `scripts` dir for the available serialization formats.

The standard mapping from Schema.org to EDM is based on the requirements described in ["Guidelines for providing and handling Schema.org metadata in compliance with Europeana"](https://docs.google.com/document/d/1ffQt8LyHuldWMbFr79HEZ-_vQUVpcNqaCOAqzN12ycg).

Europeana requires a reference to the provider responsible for performing the lod-aggregation. This can be specified during run time using the `--provider {provider naam}` option, by setting the VAR_PROVIDER environment variable using the `.env` file.

## Validator

Run the validator using the following command:

```bash
docker-compose run --rm --user 1000:1000 validate starter.sh \
  --data {data file} \
  --shape {shape file} \
  --output {result file}
```

The result file is an formal SHACL validation report written in Turtle and can be found in the `data` dir. See the [`shapes` dir](./shapes) for more info on the available shape files and tools available for developping and debugging.

## Zip

Zip the result for transport to Europena:

```bash
# note the ./data in this command!
gzip ./data/{file name}
```
The result can be found in the `data` dir with the extension `.gz`
