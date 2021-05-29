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
