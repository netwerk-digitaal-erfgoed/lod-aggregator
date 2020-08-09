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

* Dataset from the Swedish National Heritage Board (SOCH)

  * [Objects from the The Royal Armoury Museum](./SOCH)

Quantative test results:

Provider | dataset name | crawl type | result EDM file (NDE server) | visible in Europeana | # triples | size | crawling time (sec) | # crawled resources | mapping time (sec)
---------|--------------|------------|------------------------------|----------------------|-----------|------|---------------------|---------------------|-------------------
EKT | ecc-books | dump | [ecc-books-edm.zip (30K)](http://cclod.netwerkdigitaalerfgoed.nl/ecc-books-edm.zip) | [preview Metis](https://metis-preview-portal.eanadev.org/en/search?query=edm_datasetName%3A268_%2a) | 1416 | 420K | 26.87 | 1? | 0.387
EKT | ecc-sculptures | dump | [ecc-sculptures-edm.zip (35K)](http://cclod.netwerkdigitaalerfgoed.nl/ecc-sculptures-edm.zip) | - | 1152 | 366K | 25.98 | 1? | 0.367
EKT | ecc-photographes | dump | [ecc-photographs-edm.zip (30K)](http://cclod.netwerkdigitaalerfgoed.nl/ecc-photographs-edm.zip) | - | 1113 | 296K | 26.09 | 1? | 0.414
EKT | ecc-paintings | dump | [ecc-paintings-edm.zip (37K)](http://cclod.netwerkdigitaalerfgoed.nl/ecc-paintings-edm.zip) | - | 1136 | 370K | 25.75 | 1? | 0.372
NDE | kb-centsprenten | links | [centsprenten-edm.zip (1.7M)](http://cclod.netwerkdigitaalerfgoed.nl/centsprenten-edm.zip) | - | 41977 | 5.4M | 633.15 | 1255 | 3.44 |
NDE | nmvw | dump | [nmvw-edm.zip (870M)](http://cclod.netwerkdigitaalerfgoed.nl/nmvw-edm.zip) | - | 14.945.723 | 2.0 G | 108.28 | 1 | 531.4
NLP | rnod | dump | [rnod-edm.zip (118M)](http://cclod.netwerkdigitaalerfgoed.nl/rnod-edm.zip) | - | 3.030.649 | 390M | 175,7 | 1 | no conversion needed
FINNA | fennica | sparql | [fennica-edm.zip (56M)](http://cclod.netwerkdigitaalerfgoed.nl/fennica-edm.zip) | - | 33.967.718 | 4.4G | 24646 | 48216 | 281.13
SOCH | LSH | dump | [soch-lsh-edm.zip (48M)](http://cclod.netwerkdigitaalerfgoed.nl/soch-lsh-edm.zip) | - | 3.491.551 | 528M | 99.7 | 2 | 112.6
* tests run on laptop with i7-8550U CPU / 1.80GHz / 8-core | 16Gb Memory ; JVM run with `-Xmx12G` option

* \# triples measured  with `wc -l` on .nt file
* crawling time measured with bash `time` prefix
* mapping time measured through jena sparql `-time` option