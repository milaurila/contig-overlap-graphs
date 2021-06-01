awk '{if ($1<$2) print $2" "$1; else print $1" "$2}' < ../../../data/sample_translated_edges.dat | sort -n -k 1 > ../../../data/sample_translated_edges_reordered.dat
