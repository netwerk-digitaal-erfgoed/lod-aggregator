# Test run with the Nationaal Museum van Wereldculturen dataset

URL of the dataset description (temporarily): <http://cclod.netwerkdigitaalerfgoed.nl/nmvw-description.ttl>

The dataset description was constructed and published for testing purposes. The dataset description points to a online dump file with the complete data. Because the dataset description was made by the test team, the download and validation steps were skipped.  

## Download the dataset

```bash
crawl.sh --dataset-uri <http://cclod.netwerkdigitaalerfgoed.nl/nmvw-description.ttl> --output nmvw.nt
```

The crawler.log complained about an invalid property `<exhibition>`. Inspecting the data showed that there was a long list of stale resources without any data connected to it. Both problems were fixed by editing the download file use the `sed` tool from the command line. See the file `errors_found.txt` for details.  

## Map the input data to EDM 

Next step was doing the actual conversion of the resources.

Because the data was not described with schema.org properties, we had to write a special sparql construct query to do the conversion, see `nmvw2edm.rq` for the details.

```bash
map.sh --data nmvw.nt --query nmvw2edm.rq --output nmvw-edm.rdf
```

Note: in `.env` `VAR_PROVIDER` was set to `NDE` to set the `edm:provider` property in this query.

## Validate the data

Validate the EDM data using SHACL shape constraints

```bash
validate.sh --data nmvw-edm.rdf
```

The validator checked **731.780** resources and found only **614** resources that failed to meet the SHACL constraints. All were related to the same problem: "At least one the following properties should be present: dc:subject, dc:type, dcterms:spatial or dcterms:temporal". A detailed list can be found in the saved errors.txt file.

## Prepare for delivery to Europeana

```bash
convert.sh --data nmvw-edm.rdf --output nmvw-edm.zip
```
