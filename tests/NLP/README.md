# Test run with the Registo Nacional de Obras Digitais (RNOD) - National Library of Portugal

Canonical URL of the dataset: <http://oai.bn.pt/conjunto_de_dados/rnod-europeana.rdf>

## Download the dataset description

```bash
crawl.sh --dataset-uri http://oai.bn.pt/conjunto_de_dados/rnod-europeana.rdf --description-only --output rnod-description.nt
```

## Validate the dataset description

Before processing the complete data we validated the dataset description. We used a SHACL shape file defined for datasets with a dump as distribution method described in the Schema.org ontology:  

```bash
validate.sh --data rnod-description.nt --shape shacl_dataset_dump_schema.ttl
```

According to `errors.txt` the validation was succesful.

## Download the complete dataset

```bash
crawl.sh --dataset-uri http://oai.bn.pt/conjunto_de_dados/rnod-europeana.rdf --output rnod.nt
```

The download completed succesfully but the crawler found 721 errors: _"Bad character in IRI (space)"_.
See `crawler.log` for more details.

## Fix the downloaded data

Because the downloaded RDF is already in EDM format no mapping is neccessary. So we can run a check on the downloaded data. But the validator crashes when running with on the `rnod.nt` datafile, probably caused by te URI problems we saw when processing the downloading the file.  

A workaround was to convert the downloaded N-triples file to a RDF/XML serialization using the following command:

```bash
serialize.sh --data rnod.nt --format RDF/XML --output rnod.rdf
```

Altough this generates warnings and an error it does produce a valid RDF/XML file which we can process.

## Validate the EDM data

```bash
validate.sh --data rnod.rdf
```

The validation resulted in number of errors, see `errors-rnod.txt` for a complete report. The main problems found are resources without an `edm:rights` property and (some SHACL debugging was neccessary here) resources without a `edm:shownBy` or `edm:shownAt` property.

## Prepare for delivery to Europeana

The next step would be running the convert command in order to prepare the data for delivering to Europeana.

```bash
convert.sh --data rnod.rdf --output rnod-edm.zip
```
