# Estágio de construção da Imagem
# Usa uma imagem base Java 17 (JDK) para executar a aplicação.
FROM eclipse-temurin:17-jdk-focal

# Define o diretório de trabalho padrão no contêiner
WORKDIR /app

# Copia o JAR que o Maven compilou na etapa anterior do Jenkins
# O nome do JAR é baseado no seu log: DQAP-0.0.1-SNAPSHOT.jar
COPY target/DQAP-0.0.1-SNAPSHOT.jar app.jar

# Define a porta que a aplicação irá usar
EXPOSE 8080

# Comando para iniciar a aplicação quando o contêiner for executado
ENTRYPOINT ["java", "-jar", "app.jar"]