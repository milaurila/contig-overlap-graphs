numOfVertices=$(wc -l ../../../data/sample_unique_ids.dat | awk '{ print $1 }')
java -jar node_degree_dist.jar $numOfVertices ../../../data/sample_translated_edges_reordered.dat > node_degree_dist.dat
