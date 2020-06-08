#!/bin/bash

set -e

scripts_dir=$(cd $(dirname $0) && pwd -P)
source $scripts_dir/utils.sh

# the Jena java tools run in memory so we increase the default heapsize maximum
JVM_ARGS="-Xmx8G"
export JVM_ARGS

# Defaults which can be set through .ENV or through the 'environment' command in docker-compose config
if [ -z $BASE_DIR ]; then
    BASE_DIR="opt"
fi

if [ -z $SHAPE_DIR ]; then
    SHAPE_DIR="shapes"
fi

if [ -z $DATA_DIR ]; then
    DATA_DIR="data"
fi

if [ -z $QUERY_DIR ]; then
    QUERY_DIR="queries"
fi

if [ -z $VAR_PROVIDER ]; then
    VAR_PROVIDER="unknown"
fi

# default format for map and serialize service
format='RDF/XML'

# supported RDF formats are: 
#    Turtle
#    RDF/XML
#    N-Triples
#    JSON-LD
#    RDF/JSON
#    TriG
#    N-Quads
#    TriX
#    RDF Binary
#
# other non-RDF formats are: text, XML, JSON, CSV, TSV.

# Parse command line arguments
while [[ "$#" > 1 ]]; do case $1 in
    --shape) 
      rel_shape_file=$SHAPE_DIR/$2
      shape_file=/$BASE_DIR/$rel_shape_file
      shift; shift
      ;;
    --data)
      rel_data_file=$DATA_DIR/$2
      data_file=/$BASE_DIR/$rel_data_file
      shift; shift
      ;;
    --output)
      rel_output_file=$DATA_DIR/$2
      output_file=/$BASE_DIR/$rel_output_file
      shift; shift
      ;;
    --query)
      rel_query_file=$QUERY_DIR/$2
      query_file=/$BASE_DIR/$rel_query_file
      shift; shift
      ;;
    --format)
      format=$2
      shift; shift
      ;;
    --dataset-uri)
      dataset_uri=$2
      shift; shift
      ;;
    --description-only)
      description_only="true"
      shift
      ;;
    --provider)
      VAR_PROVIDER=$2
      shift; shift
      ;;
    *) break;;
    esac; 
done


case $TOOL in
  crawl) 
    echo
    echo "Checking input parameters..."
    echo
    check_arg_and_exit_on_error "dataset-uri" $dataset_uri
    check_arg_and_exit_on_error "output" $output_file
    if [ -z $description_only ]; then
      echo "Starting crawling dataset $dataset_uri..."
      cd /app/crawler/
      ./crawler.sh -dataset_uri $dataset_uri -output_file $output_file &> /$BASE_DIR/$DATA_DIR/crawler.log
      echo
      echo "Ready, results (if any) written to $rel_output_file, check $DATA_DIR/crawler.log for details..."
    else
      echo "Starting crawling dataset $dataset_uri..."
      cd /app/crawler/
      ./crawler.sh -dataset_uri $dataset_uri -dataset_description_only -output_file $output_file &> /$BASE_DIR/$DATA_DIR/crawler.log
      echo
      echo "Ready, results (if any) written to $rel_output_file, check $DATA_DIR/crawler.log for details..."
    fi
    ;; 
    convert) 
    echo
    echo "Checking input parameters..."
    echo
    check_arg_and_exit_on_error "data" $data_file
    check_arg_and_exit_on_error "output" $output_file
    echo "Starting conversion to XML for $data_file..."
    cd /app/crawler/
    ./rdf2edm.sh -input_file $data_file -output_file $output_file &> /$BASE_DIR/$DATA_DIR/converter.log
    echo
    echo "Ready, results (if any) written to $rel_output_file, check $DATA_DIR/converter.log for details..."
    ;; 
  validate) 
    echo
    echo "Checking input parameters..."
    echo
    check_arg_and_exit_on_error "shape" $shape_file
    check_arg_and_exit_on_error "data" $data_file
    check_arg_and_exit_on_error "output" $output_file
    echo "Starting SHACL validation with shapes: $rel_shape_file on input data $rel_data_file..."
    shacl validate --shapes $shape_file --data $data_file > $output_file
    echo
    echo "Ready, results written to $rel_output_file..."
    ;;
  map) 
    echo
    echo "Checking input parameters..."
    echo
    if [ "$VAR_PROVIDER" == "unknown" ]; then
       echo "Eror: Value Provider can not be 'unknown'!"
       echo "Please specify provider throug environment (VAR_PROVIDER) variable or option {--provider <name>}."
       exit 1
    fi
    check_arg_and_exit_on_error "query" $query_file
    check_arg_and_exit_on_error "data" $data_file
    check_arg_and_exit_on_error "output" $output_file

    # Replace the VAR_PROVIDER text in the query with $VAR_PROVIDER
    # Make a copy to keep the orginal query untouched.
    tmp_query=/$BASE_DIR/$QUERY_DIR/tmp.rq
    cp $query_file $tmp_query
    sed -i "s/VAR_PROVIDER/$VAR_PROVIDER/g" $tmp_query
    echo "Starting conversion with query: $rel_query_file and input data: $rel_data_file..."
    sparql --query $tmp_query --data $data_file --results $format --time > $output_file
    echo "Ready, results written to $rel_output_file..."
    ;;
  serialize) 
    echo
    echo "Checking input parameters..."
    echo
    check_arg_and_exit_on_error "data" $data_file
    check_arg_and_exit_on_error "output" $output_file
    check_arg_and_exit_on_error "format" $format
    echo "Starting serialization to $format format on input data: $rel_data_file..."
    riot --output=$format $data_file > $output_file
    echo
    echo "Ready, results written to $rel_output_file..."
    ;;
  *)
    echo "error: environment variable TOOL invalid or unset: $TOOL";;
esac
