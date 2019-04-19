## Required tools

[Grobid](https://github.com/kermitt2/grobid)



## Quasi-Gold Standard Calculator

sh qgscalc.sh samples/QGS.bib samples/DS4.bib  samples/DS1.bib samples/DS2.bib samples/DS3.bib 

sh qgscalc.sh samples/QGS.bib samples/DS4.bib  samples/DS1.bib samples/DS2.bib samples/DS3.bib > results.csv

## Create a Bibtex file for each PDF in a given folder

sh pdf2refbibtex_batch.sh /tmp/pdffolder

## Convert a BibTex ref. database to CSV and redirect it to STDOUT

sh bibtex2csv.sh /tmp/refdb.bib

## Extract all the references from a set of Bibtex files, send details as CSV to STDOUT

sh bibtex2csv_batch.sh /tmp/snowbib > /tmp/snowballrefs.csv


## Related tools

The cb2Bib is a free, open source, and multiplatform application for rapidly extracting unformatted, or unstandardized bibliographic references from email alerts, journal Web pages, and PDF files.

https://www.molspaces.com/cb2bib/
