from argparse import ArgumentParser
from numpy import histogram
from locale import setlocale, LC_NUMERIC, atof
import re
import matplotlib.pyplot as mp

def replaceAndToFloat(s):
    s = re.sub("[^0-9,]","",s)
    print(s)
    return float(atof(s.replace("\n", '')))
setlocale(LC_NUMERIC, '')
parser = ArgumentParser()
parser.add_argument("-f", "--file", dest = "filename")
args = parser.parse_args()
print(args)
f = open(args.filename, "r")
lines = f.readlines()
numbers = []
numbers = [replaceAndToFloat(f) for f in lines]
counts, bins = histogram(numbers)
mp.hist(numbers,50, density=True, facecolor='g', alpha=0.75)
mp.show()
