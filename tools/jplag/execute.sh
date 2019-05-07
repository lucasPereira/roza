#!/bin/bash

java -jar tool/jplag-2.11.9.jar \
	-t 1 \
	-l java17 \
	-m 0% \
	-vl \
	-o ../../execution/measurer/log.txt \
	-r ../../execution/measurer \
	-s \
	../../execution/materializer
