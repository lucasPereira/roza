#!/bin/bash

java -jar tool/simian-2.5.10.jar \
	-formatter=xml \
	-threshold=2 \
	-language=java \
	../../execution/materializer/*.java
