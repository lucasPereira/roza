#!/bin/bash

sudo apt-get install -y bison
sudo apt-get install -y flex

cd tool/src/main
./build.sh
