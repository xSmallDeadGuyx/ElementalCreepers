#!/bin/sh
MCP=../..
cp -r * $MCP/src/minecraft/
pushd $MCP
./recompile.sh
popd
