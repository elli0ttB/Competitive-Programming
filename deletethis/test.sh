program="Deletethis.java"
input="001.in"
expected="001.ans"


javac -d bin $program
cat $input | java -cp bin ${program:0: -5} > output

echo output:
cat output
echo expected:
cat $expected

if diff -q output $expected > /dev/null ; then
    echo "$(tput setaf 4)SUCCESS$(tput sgr0)" ;
else
    echo "$(tput setaf 1)FAILED$(tput sgr0)" ;
fi;
