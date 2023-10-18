#!/bin/bash

echo "> #############################################################################"
cp /home/ubuntu/showfan/target/showfan.war /home/ubuntu/tomcat9/webapps/showfan.war

echo "> tomcat stop #################################################################"
/home/ubuntu/tomcat9/bin/shutdown.sh

echo "> tomcat start ################################################################"
/home/ubuntu/tomcat9/bin/startup.sh
