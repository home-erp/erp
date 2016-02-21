#!/bin/bash

 ~/wildfly/bin/jboss-cli.sh --connect --command="deploy ~/workspace/homecloud/authentication/build/libs/authentication-1.0-SNAPSHOT.war --force"
