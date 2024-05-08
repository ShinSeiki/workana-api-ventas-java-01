## Pasos previos
- [Instalar y configurar Maven](https://www.mkyong.com/maven/how-to-install-maven-in-windows/)

Ejecutar los comandos listados a continuación.
```bash
cd additional_libs
mvn install:install-file -Dfile=jss-4.2.5.jar -DgroupId=org.mozilla -DartifactId=jss -Dversion=4.2.5 -Dpackaging=jar
mvn install:install-file -Dfile=MITyCLibAPI-1.0.4.jar -DgroupId=es.mityc.jumbo.adsi -DartifactId=MITyCLibAPI -Dversion=1.0.4 -Dpackaging=jar
mvn install:install-file -Dfile=MITyCLibCert-1.0.4.jar -DgroupId=es.mityc.jumbo.adsi -DartifactId=MITyCLibCert -Dversion=1.0.4 -Dpackaging=jar
mvn install:install-file -Dfile=MITyCLibOCSP-1.0.4.jar -DgroupId=es.mityc.jumbo.adsi -DartifactId=MITyCLibOCSP  -Dversion=1.0.4 -Dpackaging=jar
mvn install:install-file -Dfile=MITyCLibPolicy-1.0.4.jar -DgroupId=es.mityc.jumbo.adsi -DartifactId=MITyCLibPolicy -Dversion=1.0.4 -Dpackaging=jar
mvn install:install-file -Dfile=MITyCLibTrust-1.0.4.jar -DgroupId=es.mityc.jumbo.adsi -DartifactId=MITyCLibTrust -Dversion=1.0.4 -Dpackaging=jar
mvn install:install-file -Dfile=MITyCLibTSA-1.0.4.jar -DgroupId=es.mityc.jumbo.adsi -DartifactId=MITyCLibTSA -Dversion=1.0.4 -Dpackaging=jar
mvn install:install-file -Dfile=MITyCLibXADES-1.0.4.jar -DgroupId=es.mityc.jumbo.adsi -DartifactId=MITyCLibXADES -Dversion=1.0.4 -Dpackaging=jar
mvn install:install-file -Dfile=xmlsec-1.4.2-ADSI-1.0.jar -DgroupId=es.mityc.jumbo.adsi -DartifactId=xmlsec-1.4.2-ADSI -Dversion=1.0 -Dpackaging=jar
mvn install:install-file -Dfile=veronica-jaxb-1.0.0-SNAPSHOT.jar -DgroupId=ec.veronica -DartifactId=veronica-jaxb -Dversion=1.0.0-SNAPSHOT -Dpackaging=jar
mvn install:install-file -Dfile=veronica-soap-1.0.0-SNAPSHOT.jar -DgroupId=ec.veronica -DartifactId=veronica-soap -Dversion=1.0.0-SNAPSHOT -Dpackaging=jar
```