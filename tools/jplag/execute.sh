#!/bin/bash

java -jar tool/jplag-2.11.9.jar \
	-vlpd \
	-l java17 \
	-m 0% \
	-t 1 \
	-s ../../execution/materializer \
	-r ../../execution/measurer \
	-o ../../execution/measurer/log.txt
