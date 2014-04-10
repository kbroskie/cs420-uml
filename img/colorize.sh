#!/bin/sh

for FILE in *.png
do
	convert $FILE -colorize 50,50,50,0 +antialias down/$FILE
done
