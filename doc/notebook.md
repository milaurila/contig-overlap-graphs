# DA3018 VT21 - Project Notebook

### 2021-05-28

#### Directory structure

Created a first, maybe naive, folder structure with some inspiration from
[A Quick Guide to Organizing Computational Biology Projects](https://journals.plos.org/ploscompbiol/article?id=10.1371/journal.pcbi.1000424).
This structure is very susceptible to change.

* da3018_project
    * data: the full contigs file resides here. Might be used for samples,
    cleaned data, intermediate data files...
    * doc: notebook and report.
    * experiments:
        *  > The plan is to write and run test code here and push the final
        solution to a top "script" folder or the like later.
           > Subdirectories of "experiments" are given descriptive names of the
           tasks the scripts should accomplish, these are further divided into
           chronological, dated, folders:
        * go_to_jupiter
            * 2001-02-22
                * moonolith.sh
            * 2002-10-26
                * daisy.sh
    * project_statement: pdf files from Athena on project info.

Letting git ignore all data files (.dat) for the moment; no need to push large
amounts of data to the remote repository.

#### Getting sample data

To avoid running tests on the full set of contigs we need to grab some sample
lines.
*For the moment "some" means a million.*

An easy way is to head and tail an interval of a million lines on the contigs
file.
If the contigs were added in some systematic fashion this would however not
yield a random sample since we would grab a million consecutive lines.

Wrote a script with this strategy that gets a million lines in about 3 seconds
(wall time).
Not satisfied with the non-randomness of this.

### 2021-05-29

#### Getting sample data

Discovered the Unix "shuf" command which grabs *N* random lines from a file.
We get *N*=1,000,000 random lines in about 14 seconds. The sample file generated
from this will be used for tests.

#### Cleaning data

Looking into cleaning the data, "reducing the graph", by discarding
contig pairs where one or both of the overlaps spans the whole contig length.
Not yet sure how to make sure that no contig is "unjustly" lost in this process.
Might return to this later.

### 2021-06-01

#### Preparing the data

Since we are only interested in the contig identifiers and their relationships,
not the overlap information etc., we have to extract the relevant information
from the contig data file. The three scripts below prepares the data for
future analysis.

* get_id_columns.sh

Extract the first two columns, containing the contig identifiers, and
put them in a new file.

* get_unique_ids.sh

Extract the unique identifiers and put them in a new file. This will be used in
the translation of the string identifiers to integers.

* reorder_edges.sh

Puts the smallest (integer) ID in the first column and sorts the pairs based
on this. The algorithm(s) for solving the computational problem (mainly
constructing a graph) has not been considered yet so this step might be
superfluous. It feels good to have the option to do this at the moment.


#### Translating the contig identifiers to integers

As hinted in the project statement, using the string format of the identifiers
is a waste of space. A small java program "translates" these strings to unique
integers.

* Translate.java

Reads the output file from `get_unique_ids.sh` and pairs each string ID with
an integer in a hash map. Then reads the output file from `get_id_columns.sh`,
looks up the string ID in the hash table and outputs it's integer.

* translate_edges.sh

Handles the input and output files to `Translate.java`.

#### Flowchart

Times for sample file of one million pairs.

1. `get_id_columns.sh < contigs.dat > ids.dat`    0.4 seconds
2. `get_unique_ids.sh < ids.dat > unique_ids.dat`   12.7 seconds
3. `translate_edges.sh < unique_ids.dat & ids.dat > translated_edges.dat`     5.5 seconds
4. `reorder_edges.sh < translated_edges.dat > reordered_translated_edges.dat`   1.1 seconds

When all put together in the future the intermediate files should be deleted
after use.

### 2021-06-02

#### Constructing the graph

Since the graph is already implicitly defined by the contigs data set, our
explicit ditto can be made rather simple, i.e. without any methods to
manipulate the graph (add or remove vertices for example).

The number of verticies and edges is 11393435 and 64056772 respectively which
means that the graph is sparse. An adjacency-list representation is used
because of this, an adjacency-matrix would use too much space.

Because the size of the vertex set is already known we can use an array to
store the vertices. If this was not the case we would probably use a hash table
which is dynamic. With an array we get rid of the need to compute hashes on
vertex look-ups.

Each vertex object is given a neighbour attribute in the form of an ArrayList.
As we don't know the neighbourhood of the vertices this data structure must be
dynamic. It might be beneficial to use a linked list instead, since I suspect
that we'll be inserting elements at the end of the list more often than
grabbing them by index (for example). The ArrayList is convenient however,
might revisit the topic if time allows (or disallows because of high time
complexity).
Main concern stems from not yet knowing the node degree
distribution, if it's high that means that the ArrayList will have to resize
and copy the underlying array more often than not.

`Graph.java` takes the number of vertices and the edge set (the "translated"
contigs file) and constructs a graph from them. This should be of time
complexity *O*(|_V_|+ |_E_|).

#### Calculating node degree distribution

With the graph constructed this task is rather straight forward. The ArrayList
`size()` method gives an easy way to get the degree of each node.

`NodeDegreeDistribution.java` creates a hashmap with the sizes as keys and
tallies the number of vertices who has that amount of neighbours.
Should be of time complexity *O*(|_V_|).

#### Translating the contig identifiers to integers (shamefully re-visitied)

As mentioned in yesterdays entry (2021-06-01) the translation is preceded by
an extraction of the unique identifiers. This uses the Unix `sort` command
which employs the merge sort algorithm with worst-case performance
*O*(_n_ lg _n_). The thought was to eliminate unnecessary look-ups.
We can however probably write a pure Java implementation of the translation
using a hashmap that scans the entire edge set. Since the hashmap operations
have constant time complexity this should beat the previous tactic by quite a
bit.

Will look into this next.

P.S. Maybe the unique ID grabbing can be used to get the vertex set size from
random data samples and aid in exploring the time complexity.

### 2021-06-03

#### Speeding up the translation

Java hashmap implementation as proposed yesterday runs about three times faster
than the previous algorithm (from about 14 + 4 to 6 minutes on the entire edge
set). Sweet!

The graph constructor program needs the number of unique identifiers to create
the adjacency-array. With the new translation this information is lost in
the pipeline and must be hardcoded somewhere (problably in a driver script).

Hopefully we can make the assumption that the biologists provide us with the
number of unique contigs whenever they want us to analyze a graph for them.

#### Finding components

Wrote a first draft of an algorithm to find and count the number of components
and their size distribution based on Lars's presentation of
applications of depth-first-search in the lecture 12 discussion.

If the implementation is correct, it works well for test sets of somewhere
between 250,000 and 500,000 random edges. For our sample file of a million
pairs it runs into a stack overflow, maybe because of to deep a recursion,
maybe because it is poorly written, maybe they're not mutually exclusive.

Back to the drawing board.

### 2021-06-04

Computational problem solved (hopefully) for full, non-reduced, graph
in 6min 25s with peak RAM usage of around 3.7 GB!

#### Using a BFS for component hunting

We could probably write an iterative implementation of DFS to get around the
recursion problem. Closer at hand was the BFS presented in lecture 12.
Lars noted that the search will stop after traversing a component and not reach
other, disconnected, components. This is because the algorithm performs the
BFS on a single starting node. If we iterate the BFS over the graph's
adjacency-array the search yields all the components. (I'll have to examine
the time complexity between the two algorithms more carefully, even though they
are in the same family of functions, *O*(|_E_| + |_V_|)).

The BFS does add overhead in form of the Queue (LinkedList).

#### Finding duplicate pairs in the edge set

Good thing I remembered to check for this.

The following pipeline reorders the edges by size (smallest first), sorts them
and then merges duplicates.

`awk '{ if ($1<$2) print $1" "$2; else print $2" "$1 }' | sort | uniq`

The time complexity of `sort` is, allegedly, *O*(_n_ lg _n_). I'll have to
look up what `uniq` is doing but my guess is that it uses some sort of HashSet
so that the time complexity is *O*(_n_).


#### ArrayList vs. LinkedList

This was a rabbit hole of blog- and forum posts with opinions, benchmarks and
references to the Java source code. As mentioned in the entry on 2021-06-02
there are, theoretical, pros and cons to these in different use cases.
However, the performance of the practical implementations doesn't seem to be
as clear-cut.

To get a definitive answer for our case of storing the vertices'
neighbours, and the operations we apply on them, we should simply try both and
get some numbers. This is _not_ a priority at the moment. (Sticking with
ArrayList)

#### Constructing the pipeline

When testing the algorithms it was convenient to isolate the transitional
stages of the data by storing them in files. Reading and writing from/to
disk (technically flash) is slow and there's really no reason to use these
intermediate files in the final, total, algorithm (if we want to examine
the data at different stages we can use the old versions of the programs in the
`experiments` folder).

For this reason all the programs were re-written to be used in a Unix pipeline.
This means that their input comes from the console's Standard Out. From start
to finish, the pipeline below produces the answers to the computational
problem (this will be put in an executable).

`numOfNodes=11393435; awk '{ print $1,$2 }' < contigs.dat | java -jar translate.jar |
awk '{ if ($1<$2) print $1" "$2; else print $2" "$1 }' | sort | uniq |
java -jar graphify.jar $numOfNodes > result.dat`

The contents of `result.dat` are planned to be plotted.

As in _ArrayList vs. LinkedList_ there was a concern for
the best way to stream stdout into the Java programs. Claims were found that
a BufferedReader is around ten times faster than a Scanner when inserting
into an array. The Scanner does however perform a parsing of the input for us
whereas the BufferedReader does not. Whether it is faster to read the raw bytes
with a BufferedReader, and then parse them to Integers "manually", compared to
letting a Scanner do that for us, is unknown to me. Sticking with the Scanner
out of convenience. (Again, tests would have to be performed.)

#### Controlling the results

Embarrassingly, there hasn't been time for a proof of correctness of the
algorithm (I feel like this should _really_ be done). Some easy checks have
been done.

- [ ] Proof of correctness.
- [x] Summing the number of vertices with each degreee, yields the number of vertices (|_V_|).
- [x] Summing the number of components with each size, yields the number of components.
- [x] Summing the product of each size and the number of components with that size, yields the number of vertices.

### 2021-06-05

#### Plotting

A rudimentary Python script plots the histogramically collected data in bar
plots.

Apparently there's a monster in the data, a component of the graph with size
7,282,226. This really skews the plot of the component size distribution as
basically all other components have a size under 600 (a handful is slightly
above). These outliers were collected from the data and added to the plot in
text.

The Python script can be tacked on to the pipline to produce the plots
"directly" from the original data file. As it is now, the pipeline produces
a results file and the plots are produced from this file.

#### Directory structure

Moved final source code and jar files to folders in top directory. Added
a driver script to root directory.

