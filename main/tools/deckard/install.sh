#!/bin/bash

sudo apt-get install -y bison
sudo apt-get install -y flex
sudo apt-get install -y build-essential

rm -rf tool
unzip deckard-2.0.zip
mv Deckard-* tool
cd tool/src/main
./build.sh
