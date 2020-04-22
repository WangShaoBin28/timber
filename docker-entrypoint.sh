#!/bin/sh
set -e

deployment_name=`echo $HOSTNAME | awk -F"-" '{print $1 "-" $2 "-" $3 }'`

echo $deployment_name

java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Delastic.apm.service_name=$deployment_name -Darms.appName=$deployment_name -Dskywalking.agent.service_name=$deployment_name -jar /app.jar
