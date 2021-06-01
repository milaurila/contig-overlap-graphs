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
