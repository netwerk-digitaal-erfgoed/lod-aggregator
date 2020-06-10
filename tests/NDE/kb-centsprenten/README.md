# Test run with the KB-Centsprenten dataset

Canonical URL of the dataset: <http://data.bibliotheken.nl/id/dataset/rise-centsprenten>

## Download the dataset description

Start with downloading the dataset description only

```bash
crawl.sh --dataset-uri http://data.bibliotheken.nl/id/dataset/rise-centsprenten --description-only --output centsprenten-description.nt
```

## Validate the dataset description

Before processing the complete data we validated the dataset description. We used a SHACL shape file defined for datasets with URI lists using the Schema.org ontology:

```bash
validate.sh --data centsprenten-description.nt --shape shacl_dataset_list_schema.ttl
```

The `errors.txt` complains about a missing or invalid 'schema:name', this property is required according to [the Europeana specification for specifying a Linked Data set](https://zenodo.org/record/3817314).
This in not a blocking issue, it is possible to continue with the full download.

## Download the dataset

```bash
crawl.sh --dataset-uri http://data.bibliotheken.nl/id/dataset/rise-centsprenten --output centsprenten.nt
```

## Map the schema.org data to EDM data

Next step was doing the actual conversion of the resources.

```bash
map.sh --data centsprenten.nt --output centsprenten-edm.rdf
```

Note: in `.env` `VAR_PROVIDER` was set to `KB` to set the `edm:provider` property in this query.

## Validate the data

Validate the EDM data using SHACL shape constraints

```bash
validate.sh --data centsprenten-edm.rdf
```

According to `errors.txt` the validation was succesful.

## Prepare for delivery to Europeana

```bash
convert.sh --data centsprenten-edm.rdf --output centsprenten-edm.zip
```
