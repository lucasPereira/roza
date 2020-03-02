#!/bin/bash

java -jar tool/jplag-2.11.9.jar \
	-t 1 \
	-l java17 \
	-m 0% \
	-vl \
	-o ../../main/exec/measurer/log.txt \
	-r ../../main/exec/measurer \
	-s \
	../../main/exec/materializer
