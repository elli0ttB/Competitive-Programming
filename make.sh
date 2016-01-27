#!/bin/bash

if [[ -z $1 ]] ; then
    echo "This script requires the fileName of a Kattis problem as the only argument.\
 Check the url for the part after 'problems'; e.g. https://open.kattis.com/problems/antiarithmetic\
 would require \`./make.sh antiarithmetic\`." | fold -sw 95 
    exit 1
fi
mkdir $1 && cd $1

sampleProblems="https://open.kattis.com/problems/$1/file/statement/samples.zip";
wget $sampleProblems -O samples.zip
unzip samples.zip
rm samples.zip

input=$(ls -1 | grep .in)
expected=$(ls -1 | grep .ans)

className=${1^} # capitalize first letter
fileName=$className".java" 

cp ../Solution.java $fileName
sed -i "s/Solution/$className/" $fileName

cp ../test.sh .
# change first few lines to point to correct downloaded files
sed -i "1,5s/solution.ans/$expected/" test.sh
sed -i "1,5s/solution.in/$input/" test.sh
sed -i "1,5s/Solution.java/$fileName/" test.sh

mkdir bin
# other people may or may not want to use git, sure as hell I do.
git init
