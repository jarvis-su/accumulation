#!/bin/sh
echo "hello, input your name  "
read PERSON
echo "hello, $PERSON"

FINAL_STRING=$JAVA_HOME
readonly FINAL_STRING
echo "FINAL_STRING can not be modified, its value is $FINAL_STRING"

