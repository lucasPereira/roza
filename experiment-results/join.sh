#!/bin/bash

join -t ";" average-precision-recall-curve/deckard-10-0-0,9.csv average-precision-recall-curve/deckard-10-0-1,0.csv | join -t ";" - average-precision-recall-curve/deckard-1-0-0,9.csv | join -t ";" - average-precision-recall-curve/deckard-1-0-1,0.csv | join -t ";" - average-precision-recall-curve/deckard-10-2-0,9.csv | join -t ";" - average-precision-recall-curve/deckard-10-2-1,0.csv | join -t ";" - average-precision-recall-curve/deckard-10-2147483647-0,9.csv | join -t ";" - average-precision-recall-curve/deckard-10-2147483647-1,0.csv | join -t ";" - average-precision-recall-curve/deckard-10-4-0,9.csv | join -t ";" - average-precision-recall-curve/deckard-10-4-1,0.csv | join -t ";" - average-precision-recall-curve/deckard-1-2-0,9.csv | join -t ";" - average-precision-recall-curve/deckard-1-2-1,0.csv | join -t ";" - average-precision-recall-curve/deckard-1-2147483647-0,9.csv | join -t ";" - average-precision-recall-curve/deckard-1-2147483647-1,0.csv | join -t ";" - average-precision-recall-curve/deckard-1-4-0,9.csv | join -t ";" - average-precision-recall-curve/deckard-1-4-1,0.csv | join -t ";" - average-precision-recall-curve/deckard-20-0-0,9.csv | join -t ";" - average-precision-recall-curve/deckard-20-0-1,0.csv | join -t ";" - average-precision-recall-curve/deckard-20-2-0,9.csv | join -t ";" - average-precision-recall-curve/deckard-20-2-1,0.csv | join -t ";" - average-precision-recall-curve/deckard-20-2147483647-0,9.csv | join -t ";" - average-precision-recall-curve/deckard-20-2147483647-1,0.csv | join -t ";" - average-precision-recall-curve/deckard-20-4-0,9.csv | join -t ";" - average-precision-recall-curve/deckard-20-4-1,0.csv | join -t ";" - average-precision-recall-curve/deckard-30-0-0,9.csv | join -t ";" - average-precision-recall-curve/deckard-30-0-1,0.csv | join -t ";" - average-precision-recall-curve/deckard-30-2-0,9.csv | join -t ";" - average-precision-recall-curve/deckard-30-2-1,0.csv | join -t ";" - average-precision-recall-curve/deckard-30-2147483647-0,9.csv | join -t ";" - average-precision-recall-curve/deckard-30-2147483647-1,0.csv | join -t ";" - average-precision-recall-curve/deckard-30-4-0,9.csv | join -t ";" - average-precision-recall-curve/deckard-30-4-1,0.csv | join -t ";" - average-precision-recall-curve/deckard-40-0-0,9.csv | join -t ";" - average-precision-recall-curve/deckard-40-0-1,0.csv | join -t ";" - average-precision-recall-curve/deckard-40-2-0,9.csv | join -t ";" - average-precision-recall-curve/deckard-40-2-1,0.csv | join -t ";" - average-precision-recall-curve/deckard-40-2147483647-0,9.csv | join -t ";" - average-precision-recall-curve/deckard-40-2147483647-1,0.csv | join -t ";" - average-precision-recall-curve/deckard-40-4-0,9.csv | join -t ";" - average-precision-recall-curve/deckard-40-4-1,0.csv | join -t ";" - average-precision-recall-curve/deckard-50-0-0,9.csv | join -t ";" - average-precision-recall-curve/deckard-50-0-1,0.csv | join -t ";" - average-precision-recall-curve/deckard-50-2-0,9.csv | join -t ";" - average-precision-recall-curve/deckard-50-2-1,0.csv | join -t ";" - average-precision-recall-curve/deckard-50-2147483647-0,9.csv | join -t ";" - average-precision-recall-curve/deckard-50-2147483647-1,0.csv | join -t ";" - average-precision-recall-curve/deckard-50-4-0,9.csv | join -t ";" - average-precision-recall-curve/deckard-50-4-1,0.csv | join -t ";" - average-precision-recall-curve/deckard-60-0-0,9.csv | join -t ";" - average-precision-recall-curve/deckard-60-0-1,0.csv | join -t ";" - average-precision-recall-curve/deckard-60-2-0,9.csv | join -t ";" - average-precision-recall-curve/deckard-60-2-1,0.csv | join -t ";" - average-precision-recall-curve/deckard-60-2147483647-0,9.csv | join -t ";" - average-precision-recall-curve/deckard-60-2147483647-1,0.csv | join -t ";" - average-precision-recall-curve/deckard-60-4-0,9.csv | join -t ";" - average-precision-recall-curve/deckard-60-4-1,0.csv | join -t ";" - average-precision-recall-curve/jplag-10.csv | join -t ";" - average-precision-recall-curve/jplag-11.csv | join -t ";" - average-precision-recall-curve/jplag-1.csv | join -t ";" - average-precision-recall-curve/jplag-2.csv | join -t ";" - average-precision-recall-curve/jplag-3.csv | join -t ";" - average-precision-recall-curve/jplag-4.csv | join -t ";" - average-precision-recall-curve/jplag-5.csv | join -t ";" - average-precision-recall-curve/jplag-6.csv | join -t ";" - average-precision-recall-curve/jplag-7.csv | join -t ";" - average-precision-recall-curve/jplag-8.csv | join -t ";" - average-precision-recall-curve/jplag-9.csv | join -t ";" - average-precision-recall-curve/lccss.csv | join -t ";" - average-precision-recall-curve/lcs.csv | join -t ";" - average-precision-recall-curve/simian-10.csv | join -t ";" - average-precision-recall-curve/simian-2.csv | join -t ";" - average-precision-recall-curve/simian-3.csv | join -t ";" - average-precision-recall-curve/simian-4.csv | join -t ";" - average-precision-recall-curve/simian-5.csv | join -t ";" - average-precision-recall-curve/simian-6.csv | join -t ";" - average-precision-recall-curve/simian-7.csv | join -t ";" - average-precision-recall-curve/simian-8.csv | join -t ";" - average-precision-recall-curve/simian-9.csv > average-precision-recall-curve.csv