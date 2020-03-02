#!/bin/bash

java -jar jplag-2.11.9.jar \
	-t 1 \
	-l java17 \
	-m 0% \
	-vl \
	-o ../../exec/measurer/log.txt \
	-r ../../exec/measurer \
	-s \
	../../exec/materializer
