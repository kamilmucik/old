'mvn -DdevelopmentVersion=1.0.1-SNAPSHOT -DreleaseVersion=1.0.0 -Dusername=kmucik -Dpassword=!QAZse4 -Dresume=false release:prepare release:perform' 
mvn clean spring-boot:run -Plocal -pl daemon-application\ -Drun.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"  

Pomysły:
1. zrobić automatyczne aktualizowanie produktów w sklepie
2. przepisać na spring-boot 2 jsf2 + REST
3. serwis generujący obrazki/upload, generowanie miniatur https://github.com/jdmr/fileUpload
4. 