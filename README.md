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

The result file is an formal SHACL validation report written in Turtle and can be found in the `data` dir. 

Normally the validation process is run on the generated EDM datafile after running the mapper. The default `shacl_edm.ttl` shape file validates the EDM on the requirements specified by Europeana in the document mentioned above.

Note: Before crawling the complete data a validation of the dataset description is recommended to test the validity of the dataset description. The requirements for dataset descriptions that can be processed by the lod-aggregator are described in [Specifying a linked data dataset for Europeana and aggregators](https://docs.google.com/document/d/1ffQt8LyHuldWMbFr79HEZ-_vQUVpcNqaCOAqzN12ycg).

For checking the description againts these requirements one of the available SHACL shape files can be used depending on the distribution type _list, dump, query_ and schema used _VOID, DCAT, Schema.org_ used for the dataset. The naming sequence is as follows:
 `shape_dataset_{distribution type}_{schema type}.ttl`.

For example a dataset listing a set of URI and described in DCAT can be validated with the shape file `shape_dataset_list_dcat.ttl`

_Note: currently not all shape files are available yet (work in progress, check the `shapes` dir first)._

## Zip

Zip the result for transport to Europena:

```bash
# note the ./data in this command!
gzip ./data/{file name}
```
The result can be found in the `data` dir with the extension `.gz`
