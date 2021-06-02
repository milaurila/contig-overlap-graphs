awk '{if ($1<$2) print $1" "$2; else print $2" "$1}' < ../../../data/sample_translated_edges.dat | sort -n -k 1 > ../../../data/sample_translated_edges_reordered.dat
