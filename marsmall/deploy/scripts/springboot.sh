#!/bin/bash

PROJECT=marsmall
PROFILE=prod

CURR_PID=$(pgrep -f ${PROJECT}.*.jar)
if [ -n "$CURR_PID" ]; then
  echo "already running java process ---> kill -15 $CURR_PID"
  kill -15 "$CURR_PID"
  sleep 8
fi

JAR="$HOME/$PROJECT/deploy/marsmall-0.0.1-SNAPSHOT.jar"
JVM_OPTIONS="-Dspring.profiles.active=$PROFILE -Dspring.config.import=file:$HOME/$PROJECT/secret/config.yml"
LOG_PATH="$HOME/$PROJECT/$PROJECT.log"

echo "application started: $JAR"
echo "nohup "java -jar "$JVM_OPTIONS" "$JAR"" > ""$LOG_PATH"" 2>&1 &"
echo "log path: $LOG_PATH"

# When nohup errors, output errors to log file
nohup /bin/bash -c "java -jar $JVM_OPTIONS $JAR" > "$LOG_PATH" 2>&1 &
