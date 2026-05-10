#!/bin/bash

rm -rf tool
unzip deckard-2.0.zip
mv Deckard-* tool

rozaPath=`realpath ../..`

docker build -t deckard .
docker run -v $rozaPath:/roza deckard bash -c "cd external-tools/deckard/tool/src/main && sh build.sh"
