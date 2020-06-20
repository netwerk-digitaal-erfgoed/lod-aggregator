# Fixed errors in the input data

An easy way to find possible errors in the input data is to run a data conversion on the inputfile, for example like this command:

```bash
serialize.sh --data fennica.nt --format Turtle --output fennica.ttl
```

In this case a relative short list of warnings was generated. These warning can lead to errors in the next processing steps so we will do an attempt to fix them. It is of course a good pratice to backup the original file before running the commands below.

## Problem: `http:/` instead of `http://`

```bash
Code: 57/REQUIRED_COMPONENT_MISSING in HOST: A component that is required by the scheme is missing.
07:16:53 WARN  riot                 :: [line: 701360, col: 72] Bad IRI: <http:/urn.fi/URN:ISBN:978-951-768-248-0>
07:17:42 WARN  riot                 :: [line: 12804384, col: 72] Bad IRI: <http:/urn.fi/URN:ISBN:978-952-245-964-0>
07:17:58 WARN  riot                 :: [line: 17110109, col: 72] Bad IRI: <http:/www.ely-keskus.fi/uusimaa/julkaisut>
07:18:11 WARN  riot                 :: [line: 19480174, col: 72] Bad IRI: <http:/www.slav.helsinki.fi/am50/>
```

* Fix:

```bash
  sed -i 's|http:/urn.fi|http://urn.fi|g' data/fennica.nt
  sed -i 's|http:/www|http://www|g' data/fennica.nt
```

## Problem: `http:` instead of `http://`

```bash
Code: 57/REQUIRED_COMPONENT_MISSING in HOST: A component that is required by the scheme is missing.
07:18:26 WARN  riot                 :: [line: 23848661, col: 72] Bad IRI: <http:www.inf.vtt.fi/pdf/>
```

* Fix:

```bash
sed -i 's|http:www.inf.vtt.fi|http://www.inf.vtt.fi|g' data/fennica.nt
```

## Problem: probably `urn.fi/` missing

```bash
Code: 0/ILLEGAL_CHARACTER in PORT: The character violates the grammar rules for URIs/IRIs.
07:16:53 WARN  riot                 :: [line: 871346, col: 72] Bad IRI: <http://URN:ISBN:978-951-697-746-4>
```

* Fix:

```bash
sed -i 's|http://URN|http://urn.fi/URN|g' data/fennica.nt
```

## Problem: `http://http://` instead of `http://`

```bash
Code: 12/PORT_SHOULD_NOT_BE_EMPTY in PORT: The colon introducing an empty port component should be omitted entirely, or a port number should be specified.
07:17:07 WARN  riot                 :: [line: 4540492, col: 72] Bad IRI: <http://http://www.ymparisto.fi/download.asp?contentid=123014&lan=fi>
```

* Fix:
  
```bash
sed -i 's|http://http://www.ymparisto.fi/|http://www.ymparisto.fi/|g' data/fennica.nt
```

## Problem: `http:///` instead of `http://`

```bash
Code: 57/REQUIRED_COMPONENT_MISSING in HOST: A component that is required by the scheme is missing.
07:17:13 WARN  riot                 :: [line: 6211763, col: 72] Bad IRI: <http:///www.matilda.fi/servlet/page?_pageid=518,193&_dad=portal30&_schema=PORTAL30>
```

* Fix:

```bash
sed -i 's|http:///|http://|g' data/fennica.nt
```

## Problem: `urn:fi` instead of `urn.fi`

```bash
Code: 0/ILLEGAL_CHARACTER in PORT: The character violates the grammar rules for URIs/IRIs.
07:17:37 WARN  riot                 :: [line: 11293356, col: 72] Bad IRI: <http://urn:fi/URN:ISBN:978-952-245-984-8>
```

* Fix:

```bash
sed -i 's|http://urn:fi/|http://urn.fi/|g' data/fennica.nt
```

## Problem: `[` and `]` should be encoded

```bash
Code: 0/ILLEGAL_CHARACTER in QUERY: The character violates the grammar rules for URIs/IRIs.
07:17:55 WARN  riot                 :: [line: 16182136, col: 72] Bad IRI: <http://www.fskompetenscentret.fi/index.php?target=File&action=show&cms[id]=923>
07:19:15 WARN  riot                 :: [line: 29520725, col: 72] Bad IRI: <http://www.fskompetenscentret.fi/index.php?target=File&action=show&cms[id]=711>
```

* Fix:

```bash
sed -i 's|cms\[id\]|cms%5Bid%5D|g' data/fennica.nt
```

Running the serialize command from the top of document again showed that these errors were resolved.
