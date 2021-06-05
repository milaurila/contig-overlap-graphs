import matplotlib.pyplot as plt
import sys

node_degrees = []
degree_freqs = []
comp_sizes = []
size_freqs = []

# Collect data:
line = sys.stdin.readline().split()
while len(line) != 0:   # Data sets separated by empty line.
    node_degrees.append(int(line[0]))
    degree_freqs.append(int(line[1]))
    line = sys.stdin.readline().split()

line = sys.stdin.readline().split()
while len(line) != 0:   # Data sets separated by empty line.
    num_of_components = line[0]
    line = sys.stdin.readline().split()

line = sys.stdin.readline().split()
while line:     # Last data set.
    comp_sizes.append(int(line[0]))
    size_freqs.append(int(line[1]))
    line = sys.stdin.readline().split()

# Plot node degree distribution.
plt.yscale("log")
plt.bar(node_degrees, degree_freqs)
plt.xlabel("Node degree")
plt.ylabel("Frequency")
plt.title("Node degree distribution")
plt.savefig("ndd_plot.png")

plt.clf()

# Plot component size distribution.
#plt.yscale("log")
plt.xscale("log")
plt.bar(comp_sizes, size_freqs)
plt.xlabel("Component size")
plt.ylabel("Frequency")
plt.text(1000, 200000, f'Number of components: {num_of_components}')
plt.title("Component size distribution")
#plt.title("1 component of size 7,282,226 removed from data")
plt.savefig("csd_plot.png")

print("Plots of node degree and component size distributions saved to .png files.")
