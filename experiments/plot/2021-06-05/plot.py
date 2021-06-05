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

comp_outliers = [ (freq, size) for freq, size in zip(size_freqs, comp_sizes)
                                                                if size > 600 ]

# Plot node degree distribution.
plt.figure(dpi=1200)
plt.yscale("log")
plt.xlim(-15, 2400)
plt.bar(node_degrees, degree_freqs)
plt.xlabel("Node degree")
plt.ylabel("Frequency")
plt.title("Node degree distribution")
plt.savefig("ndd_plot.png")

plt.clf()

# Plot component size distribution.
plt.figure(dpi=1200)
plt.yscale("log")
plt.bar(comp_sizes, size_freqs)
plt.xlim(-2, 600)
plt.xlabel("Component size")
plt.ylabel("Frequency")
plt.text(200, 10000, f'Number of components: {num_of_components} \n\n'
        f'Outliers, size > 600, (freq, size):\n {comp_outliers}')
plt.title("Component size distribution")
plt.savefig("csd_plot.png")

print(f'Plots of node degree and component size distributions saved to .png '
        f'files.')
