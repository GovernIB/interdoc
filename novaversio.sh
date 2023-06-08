#!/bin/bash

env mvn -DgroupId=es.caib.interdoc -DartifactId=* versions:set -DnewVersion=$@  

