#!/bin/bash

java -jar jplag-2.11.9.jar \
	-t 1 \
	-l java17 \
	-m 0% \
	-vl \
	-o ../../output/measurer/log.txt \
	-r ../../output/measurer \
	-s \
	../../output/materializer
