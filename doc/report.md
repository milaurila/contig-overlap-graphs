## Calculating graph features from a contigs overlap data set

##### Algorithm overview

We want to transform the data set into a graph representation from which we can
obtain the desired features.

A dictionairy that maps each contig identifier to a graph
vertex identifier, in the form of an integer, is created. This dictionairy is
then used to translate the contig pairs to edges of the graph.

Adjacency-list representations of the graph and its components yields a
straight forward way of extracting the node degree- and component size
distributions, as well as the number of components.

### From Contig Pairs to Graph

#### Translation with time and space complexity *O*(_N_)

As we're only interested in the contig identifiers these need to be sieved
out from the data set and sent to the translator. This is done with the Unix
`awk` command. Assuming that the extraction of one pair takes constant time,
this step grows linearly in time with the number of contig pairs (_N_).

The translator stores contig identifiers and their corresponding vertex IDs
as key, value-pairs in a hash table.
For each contig pair the translator recieves, a constant amount of
computation is made in the form of assignments, hash table look
ups and system output. This step grows linearly in time and space with the
number of contig pairs.

#### Removing duplicate pairs with time complexity *O*(_N_ lg _N_)

A graph contains no duplicate edges, if a contig pair appears twice in the data
set only one instance of that pair can be sent to the graph constructor.
To remove duplicates, a small `awk` script together with the Unix commands
`sort` and `uniq` (which will be assumed to have time complexity *O*(_N_),
can't find info on this) are used.
This step is performed after the translation since both the `awk` script and
the `sort` command performs comparisons. This is faster to do for integers than
strings.

`sort` employs merge sort with worst-case time complexity *O*(_N_ lg _N_)
and will therefore be the fastest growing factor in terms of time.

#### Constructing the graph with time and space complexity *O*(|_V_| + |_E_|) and *O*(|_E_|)

The provided graph is sparse meaning that an adjacency-list representation is
more memory efficient than an adjacency-matrix.

Since we know the number of (unique) contigs in the data set, the underlying
array of the graph's adjacency-list can be populated without iterating over
the now created edge set (_E_). This is also the reason an array can be used in
the first place, since without knowledge of the size of the vertex set (_V_) we
must use a dynamic data structure. The array gives constant time look ups which
will be beneficial when populating the vertices' neighbour sets using the edge
set.

If the number of vertices is unknown, the `experiments/prep_data` folder
contains a script `get_unique_ids.sh` which will output the unique vertices
(which can then be counted).
This script is rather costly and not used here since it isn't needed.

For every edge the constructor recieves, each vertex is added to the others'
neighbour set. The neighbour set is represented by an ArrayList since we don't
know how many neighbours each vertex has. One could probably argue for the use
of a linked list here. The ArrayList uses an underlying, well, array, which
must be copied and resized when its capacity (or some sort of load factor) is
reached. If the degree of a vertex is large, the ArrayList would have to
perform these operations multiple times. If _many_ vertices have large degrees
this could perhaps add up in computational time. A linked list does not come
with this caveat but is slower to iterate over since its elements aren't
stored contiguously in memory. This might be a factor in the BFS algorithm
used to find components.
(Maybe there's a sweet spot with regards to the node degree distribution for
choosing a data structure for the neighbour sets.)

The constructor performs constant work for each vertex and each edge leading to
a linear growth in time with the sum |_V_| + |_E_|. The space complexity will
depend on the number of edges since every vertex has at least one neighbour.

### Getting Features

#### Computing node degree distribution with time complexity *O*(|_V_|)

With the graph constructed, getting the degree of each vertex is a matter of
querying the size of its neighbour-ArrayList. This takes constant time and is
done once for every vertex in _V_. The distribution is tallied in a hash table,
also taking constant time.

(I'm not sure how to calculate the space complexity for the hash table holding
the distribution. Is it dependent on the distribution itself?)

#### Finding components with time and space complexity *O*(|_V_| + |_E_|) and *O*(|_V_|)

The components are found via a breadth-first-search that uses a linked list
queue. By performing the BFS on each non-visited vertex in the adjacency-array
we can "jump" between components until every vertex has been visited, thus
finding all the components.

There was an attempt to use a recursive depth-first-search to find the
components. This worked well for smaller graphs (samples from the original
data) but ran into a stack overflow when the graph got to big. Perhaps
because the number of recursive calls, when inside a large component, gets to
high. One could write an iterative implementation of DFS to get around this
problem. The performance differences between an iterative DFS and the BFS used
in our algorithm has not been explored. The BFS was simply easy to implement
and worked off the bat.

The time complexity for a BFS is *O*(|_V_| + |_E_|) and we store all vertices
as components, meaning a space complexity of *O*(|_V_|).

#### Computing component size distribution with time complexity *O*(|_C_|)

The component size distribution is found in exactly the same way as the node
degree distribution. Constant work is done for each component in _C_. The
number of components is obtained by querying the size of the list of components
found by the BFS, meaning constant time.

### Results

The distributions are printed from the hash tables that contains them. Our
findings are plotted below.

![ndd_plot](https://user-images.githubusercontent.com/59459559/120906848-23370a80-c65d-11eb-9db5-afc51bf328ad.png)
![csd_plot](https://user-images.githubusercontent.com/59459559/120906843-1fa38380-c65d-11eb-92af-cdc1cefc3107.png)

_Have a nice summer!_
