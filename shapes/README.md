# Shape definitions for the SHACL Validation

## Background

For the validation of the incoming and converted Linked Data the Jena SHACL validator is used, see the toplevel README for running the validator service. For more info on the SHACL language see [the specification of the Shape Constraints Language (SHACL)](https://www.w3.org/TR/shacl/). 

To learn more on RDF validation in general and the details of ShEX and SHACL languages the [online book Validating RDF Data](https://book.validatingrdf.com/). For developping tests or debugging validation results use the [RDFShape tools](http://rdfshape.weso.es/) or [SHACL Playground](https://shacl.org).

## Available SHACL definitions

The main validation step is checking the converted Linked Data to check on compliance with the [Europeana Data Model defintions](https://pro.europeana.eu/files/Europeana_Professional/Share_your_data/Technical_requirements/EDM_Documentation//EDM_Definition_v5.2.8_102017.pdf). The default `shacl_edm.ttl` shape file validates the EDM on the requirements specified by Europeana.

Before crawling the complete data a validation of the dataset description is recommended to test the validity of the dataset description. The requirements for dataset descriptions that can be processed by the lod-aggregator are described in [Specifying a linked data dataset for Europeana and aggregators](https://docs.google.com/document/d/1ffQt8LyHuldWMbFr79HEZ-_vQUVpcNqaCOAqzN12ycg).

For checking the description againts these requirements one of the available SHACL shape files can be used depending on the distribution type _list, dump, query_ and schema used _VOID, DCAT, Schema.org_ used for the dataset. The naming sequence is as follows:
 `shape_dataset_{distribution type}_{schema type}.ttl`.

For example a dataset listing a set of URI and described in DCAT can be validated with the shape file `shape_dataset_list_dcat.ttl`

_Note: currently not all shape files are available yet!_
