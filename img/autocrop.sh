#/bin/sh

for FILE in *.png
do
	convert $FILE -trim +repage +antialias $FILE
done
