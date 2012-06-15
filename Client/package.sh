#!/bin/sh
pushd ../..
./reobfuscate.sh

pushd reobf/minecraft
cp -r ../../port-ecreepers/Client/mob .
zip -r ../../port-ecreepers/Client/ElementalCreepers_v2.3r2_mp_MC1.2.5.zip .
popd

popd
