# Mapping queries

## Background

The `map service` starts a container that runs a Jena sparql commandline tool executed on a specified dataset using the specified query. By using a SPARQL CONSTRUCT query the resulting output is a valid Linked Data resultset. It defaults to the Turtle serialization format but this can be overridden using the `--format` option.

The default mapping file `schema2edm.rq` is written for a Schema.org to EDM transformaton and is based on the requirements described in ["Guidelines for providing and handling Schema.org metadata in compliance with Europeana"](https://docs.google.com/document/d/1ffQt8LyHuldWMbFr79HEZ-_vQUVpcNqaCOAqzN12ycg).

Europeana requires a reference to the provider responsible for performing the lod-aggregation. Before excuting the query the [starter.sh script](../scripts/starter.sh) replaces the placeholder VAR_PROVIDER in the sparql query with the specific identifier for this configuration. The VAR_PROVIDER variable can be specified during run time using the `--provider {provider naam}` option or by setting the VAR_PROVIDER environment variable using the `.env` file.