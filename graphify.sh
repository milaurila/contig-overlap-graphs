numOfNodes=11393435;
awk '{ print $1,$2 }' < data/contigs.dat | java -jar program/translate.jar |
    awk '{ if ($1<$2) print $1" "$2; else print $2" "$1 }' | sort | uniq |
    java -jar program/graphify.jar $numOfNodes > results/results.dat;
echo Results saved in results/results.dat
python program/plot.py < results/results.dat
