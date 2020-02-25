#!/bin/sh
lein clean
lein compile
lein jar
lein install
