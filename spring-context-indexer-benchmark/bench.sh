#!/bin/sh
mvn clean package

echo "*****************************"
echo "* BENCHMARK - SINGLE THREAD *"
echo "*****************************"
java -jar target/benchmarks.jar -bm avgt -f 1 -wi 0 -i 10 -r 3s -t 1 -jvmArgs '-server -XX:+AggressiveOpts' .*Benchmark.*