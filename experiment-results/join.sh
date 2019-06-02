#!/bin/bash

touch input.csv
echo "Precisão/Revocação;Padrão" > input.csv
echo "0%;1" >> input.csv
echo "10%;1" >> input.csv
echo "20%;1" >> input.csv
echo "30%;1" >> input.csv
echo "40%;1" >> input.csv
echo "50%;1" >> input.csv
echo "60%;1" >> input.csv
echo "70%;1" >> input.csv
echo "80%;1" >> input.csv
echo "90%;1" >> input.csv
echo "100%;1" >> input.csv

curves=`ls average-precision-recall-curve`
for curve in $curves; do
	join -t ";" input.csv average-precision-recall-curve/$curve > output.csv
	rm input.csv
	mv output.csv input.csv
done
rm average-precision-recall-curve.csv
mv input.csv average-precision-recall-curve.csv
