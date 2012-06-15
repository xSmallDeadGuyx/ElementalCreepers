#!/bin/sh
rm -rf net
mkdir -p net/minecraft/server
./compile.sh
cp *.class net/minecraft/server/
zip -r ElementalCreepers-v2.3-mcpc1.2.5-r1.zip net/ 
