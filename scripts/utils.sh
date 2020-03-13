#!/bin/bash

set -e

function print_error() {
    echo $* >>/dev/stderr
}

function print_error_and_exit() {
    print_error $*
    exit 1
}

function print_progress() {
    echo $* >>/dev/stderr
}

function check_arg_and_exit_on_error() {
    if [ -z "$2" ]; then
        print_error "Error: missing argument: $1"
        print_error "Usage: $0 --$1 {$1}"
        exit 1
    fi
}

function check_env_and_exit_on_error() {
    if [ -z "$2" ]; then
        print_error_and_exit "Error: missing environment variable $1"
    fi
}
