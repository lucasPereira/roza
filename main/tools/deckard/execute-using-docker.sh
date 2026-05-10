#!/bin/bash

rozaPath=`realpath ../../../`

docker run -v $rozaPath:/roza deckard bash -c "cd main/tools/deckard && ls -l ../../../output && bash tool/scripts/clonedetect/deckard.sh"
