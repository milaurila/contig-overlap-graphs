numOfVertices=$(wc -l ../../../data/sample_unique_ids.dat | awk '{ print $1 }')
java -jar graph.jar $numOfVertices ../../../data/sample_translated_edges_reordered.dat
