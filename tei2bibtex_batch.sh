#mvn  -q exec:java -Dexec.mainClass=nl.rug.search.tools.bibtools.TEIRefExtractor -Dexec.args="$1 $2 $3 $4 $5 $6"
#echo for file in $1/*.tei ; do mvn  -q exec:java -Dexec.mainClass=nl.rug.search.tools.bibtools.TEIRefExtractor -Dexec.args=$file ; done
for file in $1/*.tei ; do mvn -X -q exec:java -Dexec.mainClass=nl.rug.search.tools.bibtools.TEIRefExtractor -Dexec.args="$file" ; done


#for file in $1/* ; do echo ===== "$file" ; done
#echo for file in $1/*.tei ; do echo ===== "$file" ; done
