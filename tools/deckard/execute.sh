#!/bin/bash

echo "#!/bin/sh" > config

echo "export MIN_TOKENS=1" >> config

echo "export STRIDE=0" >> config

echo "export SIMILARITY=1" >> config

echo "export SRC_DIR=../../execution/materializer" >> config

echo "export FILE_PATTERN=*.java" >> config

echo "export VECTOR_DIR=../../execution/measurer/vectors" >> config

echo "export CLUSTER_DIR=../../execution/measurer/cluster" >> config

echo "export TIME_DIR=../../execution/measurer/times" >> config

echo "export DECKARD_DIR=tool" >> config

echo "export VGEN_EXEC=tool/src/main/jvecgen" >> config

echo "export GROUPING_EXEC=tool/src/vgen/vgrouping/runvectorsort" >> config

echo "export CLUSTER_EXEC=tool/src/lsh/bin/enumBuckets" >> config

echo "export QUERY_EXEC=tool/src/lsh/bin/queryBuckets" >> config

echo "export POSTPRO_EXEC=tool/scripts/clonedetect/post_process_groupfile" >> config

echo "export GROUPING_S=1" >> config

echo "export MAX_PROC=1" >> config

./tool/scripts/clonedetect/deckard.sh overwrite
