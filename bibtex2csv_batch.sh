#mvn  -q exec:java -Dexec.mainClass=nl.rug.search.tools.bibtools.TEIRefExtractor -Dexec.args="$1 $2 $3 $4 $5 $6"
#echo for file in $1/*.tei ; do mvn  -q exec:java -Dexec.mainClass=nl.rug.search.tools.bibtools.TEIRefExtractor -Dexec.args=$file ; done
for file in $1/*.bib ; do mvn -q exec:java -Dexec.mainClass=nl.rug.search.tools.bibtools.BibText2CSV -Dexec.args="$file" ; done


#mvn -q exec:java -Dexec.mainClass=nl.rug.search.tools.bibtools.TEI2Bibtex -Dexec.args="$1 $2 $3 $4 $5 $6"


#for file in $1/* ; do echo ===== "$file" ; done
#echo for file in $1/*.tei ; do echo ===== "$file" ; done
