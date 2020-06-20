# Documented test runs

## Test runs to illustrate the working of the tools

In the `tests` directory the following test runs are documented:

* Datasets from the Greek National Aggregator (EKT)
  
  * [Books sample dataset](./EKT/ecc-books)
  * [Sculptures sample dataset](./EKT/ecc-sculptures)
  * [Paintings sample dataset](./EKT/ecc-paintings)
  * [Photographs sample dataset](./EKT/ecc-photographs)

* Dataset from the Dutch Digital Heritage Network (NDE)

  * [National Library - Centsprenten dataset](./NDE/kb-centsprenten)
  * [Nationaal Museum van Wereldculturen - Collection dataset](./NDE/nmvw)

* Dataset from the National Library of Portugal (BNP)
  
  * [Registo Nacional de Obras Digitais (RNOD)](./NLP/bnp-rnod)

* Dataset from the Finnish National Library (FINNA)

  * [E-books Finnisch National Library](./FINNA)

Quantative test results:

Provider | dataset name | crawl type | # triples | size	| crawling time (sec) | # crawled resources | 	mapping time (sec) | download link
---------|--------------|------------|-----------|------|---------------------|---------------------|-------------------|--------------
EKT | ecc-books | dump | 1416 | 420K | 26.87 | 1? | 0.387 | [github (30K)](./EKT/ecc-books/ecc-books-edm.zip)
EKT | ecc-sculptures | dump | 1152 | 366K | 25.98 | 1? | 0.367 | [github (35K)](./EKT/ecc-sculptures/ecc-sculptures-edm.zip)
EKT | ecc-photographes | dump | 1113 | 296K | 26.09 | 1? | 0.414 | [github (30K)](./EKT/ecc-photographs/ecc-photographs-edm.zip)
EKT | ecc-paintings | dump | 1136 | 370K | 25.75 | 1? | 0.372 | [github (37K)](./EKT/ecc-paintings/ecc-paintings-edm.zip)
NDE | kb-centsprenten | links | 41977 | 5.4M | 633.15 | 1255 | 3.44 | [nde server (1.7M)](http://cclod.netwerkdigitaalerfgoed.nl/centsprenten-edm.zip)
NDE | nmvw | dump | 14.945.723 | 2.0 G | 108.28 | 1 | 531.4 | [nde server (870M)](http://cclod.netwerkdigitaalerfgoed.nl/nmvw-edm.zip)
NLP | rnod | dump | 3.030.649 | 390M | 175,7 | 1 | no conversion needed | [nde server (118M)](http://cclod.netwerkdigitaalerfgoed.nl/rnod-edm.zip)
FINNA | fennica | sparql | 33.967.718 | 4.4G | 24646 | 48216 | 281.13 | [nde server (56M)](http://cclod.netwerkdigitaalerfgoed.nl/fennica-edm.zip)

* tests run on laptop with i7-8550U CPU / 1.80GHz / 8-core | 16Gb Memory ; JVM run with `-Xmx12G` option
* \# triples measured  with `wc -l` on .nt file
* crawling time measured with bash `time` prefix
* mapping time measured through jena sparql `-time` option