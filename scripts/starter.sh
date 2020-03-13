#!/bin/bash

set -e

scripts_dir=$(cd $(dirname $0) && pwd -P)
source $scripts_dir/utils.sh

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


# Parse command line arguments
while [[ "$#" > 1 ]]; do case $1 in
    --shape) 
      rel_shape_file=$SHAPE_DIR/$2
      shape_file=/$BASE_DIR/$rel_shape_file
      ;;
    --data)
      rel_data_file=$DATA_DIR/$2
      data_file=/$BASE_DIR/$rel_data_file
      ;;
    --output)
      rel_output_file=$DATA_DIR/$2
      output_file=/$BASE_DIR/$rel_output_file
      ;;
    --query)
      rel_query_file=$QUERY_DIR/$2
      query_file=/$BASE_DIR/$rel_query_file
      ;;
    *) break;;
    esac; shift; shift
done


case $TOOL in 
  validate) 
    echo
    echo "Checking input parameters..."
    echo
    check_arg_and_exit_on_error "shape-file" $shape_file
    check_arg_and_exit_on_error "data-file" $data_file
    check_arg_and_exit_on_error "output-file" $output_file
    echo "Starting SHACL validation with shapes: $rel_shape_file on input data $rel_data_file..."
    shacl validate --shapes $shape_file --data $data_file > $output_file
    echo
    echo "Ready, results written to $rel_output_file..."
    ;;
  map) 
    echo
    echo "Checking input parametes..."
    echo
    check_arg_and_exit_on_error "query-file" $query_file
    check_arg_and_exit_on_error "data-file" $data_file
    check_arg_and_exit_on_error "output-file" $output_file
    echo "Starting conversion with query: $rel_query_file and input data: $rel_data_file..."
    sparql --query $query_file --data $data_file > $output_file
    echo
    echo "Ready, results written to $rel_output_file..."
    ;;
  serialize) 
    echo
    echo "Checking input parametes..."
    echo
    check_arg_and_exit_on_error "data-file" $data_file
    check_arg_and_exit_on_error "output-file" $output_file
    echo "Starting serialization to XML/RDF on input data: $rel_data_file..."
    riot --output=RDF/XML $data_file > $output_file
    echo
    echo "Ready, results written to $rel_output_file..."
    ;;
  *)
    echo "error: environment variable TOOL invalid or unset: $TOOL";;
esac
