# Test run with the KB-Centsprenten dataset

Temporary URL of the dataset description <http://cclod.netwerkdigitaalerfgoed.nl/fennica.ttl>

This [dataset description](fennica-description.ttl) is an example of a SPARQL request to describe a results set. The crawler is able to process the results and crawl the complete data based on this query result.

## Download the dataset

```bash
crawl.sh --dataset-uri http://cclod.netwerkdigitaalerfgoed.nl/fennica.ttl --output fennica.nt
```

## Fixing errors in the datadump

As usual in large datafile some erors were detected, we did an attempt to correct them, see the [error report](fixed_errors.md) for more details.

## Map the schema.org data to EDM data

The next step was doing the actual conversion of the resources. The resources are modelled in the context of their bibliographical hierarchy. For the Europeana ingest we need only the instances that have a online digital representation. So we need to selected these instances from the complete data and synthesize the required properties from the instance and work level. We created [a new query](fennica-books2edm.rq) that takes care of this. This query needs be in the `queries` directory in order to use it in the `map.sh` command.

```bash
map.sh --data fennica.nt --query finna-books2edm.rq --output fennica-edm.rdf
```

In the config file `.env` the variable `VAR_PROVIDER` was set to `FINNA` to set the `edm:provider` and `edm:dataProvider` property in this query.

_Note:
In the resultset (total 37.195 CHO's) there were about 11.000 resources less than selected in the orginal query (48.216 Instances). A first analyses seems to point to problems with resolving of the URIs ("Unknown URN" message). See the [full list of Instances before and after the conversion](compare_results.txt) for more detailed information._

## Validate the data

Validate the EDM data using SHACL shape constraints

```bash
validate.sh --data fennica-edm.rdf
```

According to `errors.txt` the validation was succesful.

## Prepare for delivery to Europeana

```bash
convert.sh --data fennica-edm.rdf --output fennica-edm.zip
```

## Possible next steps

This test was run based on sparql query to describe the result set. Crawling the seperate resources took almost 7 hours. Finna also publishes a datadump, this would be a much quicker way to collect the data. A dataset description similar to the one used by the National Library of Portugal (see NLP section) could be used to set up another test. Due to time constraints this hasn't been done yet.
