
REM ELIMINAR API Y WSDL

REM DEL /s C:\Dades\Programacio\interdocProjecte\interdoc\interdoc-ws\interdoc-ws-api\src\main\java\es\caib\interdoc\ws\api
REM DEL  /s C:\Dades\Programacio\interdocProjecte\interdoc\interdoc-ws\interdoc-ws-api\src\main\resources\wsdl\ObtenerReferenciaWs.wsdl

set MAVEN_OPTS=-Xmx512m -XX:MaxPermSize=256m

mvn install -DskipTests -Dregenerateapi1 -e 

REM -Dmaven.test.skip=true