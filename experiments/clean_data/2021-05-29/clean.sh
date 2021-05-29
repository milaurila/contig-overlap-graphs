# Only print lines where neither of the contigs has an overlap spanning the their whole lengths.
cat ../../../data/sample.dat | awk '{ if (!(($6==0 && $7==$8) || ($10==0 && $11==$12))) print $0 }' > clean_sample.dat
