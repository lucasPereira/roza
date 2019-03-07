#!/bin/bash

sudo apt-get install -y bison
sudo apt-get install -y flex

cd aplicacao/src/main
./build.sh
