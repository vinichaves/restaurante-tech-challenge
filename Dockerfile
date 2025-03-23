# Usa uma imagem do Java 21 com JAR otimizado
FROM eclipse-temurin:21-jdk-alpine

# Cria um diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo JAR gerado pela aplicação (ajustado para o nome final esperado)
COPY target/*.jar app.jar

# Expõe a porta padrão da aplicação Spring Boot
EXPOSE 8080

# Executa a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
