

set MAVEN_OPTS=-Xmx512m -XX:MaxPermSize=128m

mvn clean install -DskipTests -DgenerateFromWsdl -e 

REM -Dmaven.test.skip=true