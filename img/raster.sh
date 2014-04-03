#!/bin/sh

for FILE in *.svg $1
do
	inkscape -f "$FILE" --export-width=$1 --export-height=$1 -e "${FILE%%.svg}.png"
done
