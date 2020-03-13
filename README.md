# build the crawler
docker-compose build --no-cache crawler

# download dataset description
# set -user to prevent to your UID:GID to prevent creating files owned by 'root'
docker-compose run --rm --user 1000:1000 crawler /bin/bash ./crawler.sh \
  -dataset_uri http://data.bibliotheken.nl/id/dataset/rise-centsprenten \
  -dataset_description_only \
  -output_file /opt/data/centsprenten_dataset.nt \
  -log_file /opt/crawler.log

# download complete dataset
docker-compose run --rm --user 1000:1000 crawler /bin/bash ./crawler.sh \
    -dataset_uri http://data.bibliotheken.nl/id/dataset/rise-centsprenten \
    -output_file /opt/data/centsenten.nt \
    -log_file /opt/crawler.log

# run the conversion sparql query 
 docker-compose run --rm --user 1000:1000 map starter.sh --data centsprenten.nt --query example_schema2edm.rq --output centsprenten_edm.ttl

# run validation based on SHACL shape constraints
docker-compose run --rm --user 1000:1000 validate starter.sh --data centsprenten_edm.ttl --shape example_shacl_edm.ttl --output validate_results.ttl

# run output conversion because Europeana only handles XML/RDF
# write to the host file system this way, run a batch script in the docker?
docker-compose run --rm --user 1000:1000 serialize starter.sh --data centsprenten_edm.ttl --output centsprenten_edm.rdf

