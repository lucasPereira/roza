#!/bin/bash

rozaPath=`realpath ../../../`

docker run -v $rozaPath:/roza deckard bash -c "cd main/tools/deckard && ls -l ../../exec && bash tool/scripts/clonedetect/deckard.sh"
