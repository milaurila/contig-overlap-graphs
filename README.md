# DA3018 VT21 - Project

## Run

With the original data file named `contigs.dat` in the `data` directory, run
```
./graphify.sh
```
in terminal.

## Results

Plain text file `results.dat` in the `results` directory contains output from the program in order: node degree distribution, number of components, component size distribution. Separated by blank line.

Algorithm wall time (excluding plotting): 6 minutes 25 seconds (on system below).

![ndd_plot](https://user-images.githubusercontent.com/59459559/120906848-23370a80-c65d-11eb-9db5-afc51bf328ad.png)
![csd_plot](https://user-images.githubusercontent.com/59459559/120906843-1fa38380-c65d-11eb-92af-cdc1cefc3107.png)



## System

* CPU: 6 cores @ 4.70 GHz (max), 12 MB cache
* RAM: 16 GB @ 2666 MHz
* SSD: 3500 MB/s read, 3200 MB/s write
* OS: Xubuntu 20.04.02
* Shell: Bash 5.0.17
* Java: OpenJDK 14.0.2
* Python: 3.8.5
