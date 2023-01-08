#!/bin/bash

PROJECT=marsmall
REPO="/home/ec2-user/actions-runner/_work/$PROJECT"
LOG_PATH="/var/app/log/$PROJECT.log"

CURR_PID=$(pgrep -f ${PROJECT}.*.jar)
if [ -n "$CURR_PID" ]; then
  echo "already running java process ---> kill -15 $CURR_PID"
  kill -15 "$CURR_PID"
  sleep 8
fi

# extract the largest size jar
JAR=$(ls -S $REPO/$PROJECT/build/libs/*.jar | head -n 1)

echo "application start: $JAR"
echo "log path: $LOG_PATH"

# When nohup errors, output errors to log file
nohup java -jar "$JAR" > "$LOG_PATH" 2>&1 &
