# DA3018 VT21 - Project Notebook

### 2021-05-28

#### Directory structure

Created a first, maybe naive, folder structure with some inspiration from [A Quick Guide to Organizing Computational Biology Projects](https://journals.plos.org/ploscompbiol/article?id=10.1371/journal.pcbi.1000424).
This structure is very susceptible to change.

* da3018_project
    * data: the full contigs file resides here. Might be used for samples, cleaned data, intermediate data files...
    * doc: notebook and report.
    * experiments:
        *  > The plan is to write and run test code here and push the final solution to a top "script" folder or the like later.
           > Subdirectories of "experiments" are given descriptive names of the tasks the scripts should accomplish, these are further divided into chronological, dated, folders:
        * go_to_jupiter
            * 2001-02-22
                * moonolith.sh
            * 2002-10-26
                * daisy.sh
    * project_statement: pdf files from Athena on project info.

Letting git ignore all data files (.dat) for the moment; no need to push large amounts of data to the remote repository.

#### Getting sample data

To avoid running tests on the full set of contigs we need to grab some sample lines.
*For the moment "some" means a million.*

An easy way is to head and tail an interval of a million lines on the contigs file.
If the contigs were added in some systematic fashion this would however not yield a random sample
since we would grab a million consecutive lines.

Wrote a script with this strategy that gets a million lines in about 3 seconds (wall time).
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
