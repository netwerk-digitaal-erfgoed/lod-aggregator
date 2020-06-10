#!/bin/sh

export CLASSPATH=
for jar in `ls lib/*.jar`
do
  export CLASSPATH=$CLASSPATH:$jar
done
export CLASSPATH=$CLASSPATH

java -Djava.util.logging.config.file="logging.properties" -Dsun.net.inetaddr.ttl=0 $JVM_ARGS -cp classes:$CLASSPATH eu.europeana.commonculture.lod.crawler.CommandLineInterface $*
