path %path%
java -jar -Xms800m -Xmx800m -XX:CMSInitiatingOccupancyFraction=75 -XX:MaxNewSize=256m -XX:PermSize=64m -XX:MaxPermSize=128m -XX:+CMSParallelRemarkEnabled -XX:+UseConcMarkSweepGC   ..\satellite.jar
pause