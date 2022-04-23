#!/bin/bash

java -jar simian-2.5.10.jar \
	-threshold=6 \
	-ignoreCurlyBraces+ \
	-ignoreIdentifiers- \
	-ignoreStrings- \
	-ignoreNumbers- \
	-ignoreCharacters-\
	-ignoreLiterals- \
	-ignoreVariableNames- \
	-formatter=xml \
	-language=java \
	-failOnDuplication- \
	-reportDuplicateText+ \
	-ignoreBlocks=0:0 \
	-ignoreIdentifierCase- \
	-ignoreStringCase- \
	-ignoreCharacterCase- \
	-ignoreSubtypeNames- \
	-ignoreModifiers+ \
	-balanceParentheses+ \
	-balanceSquareBrackets+ \
	../../exec/materializer/*.java >> ../../exec/measurer/report.xml
