# Etapa de construção
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

# Copia apenas os arquivos pom.xml e resolve as dependências primeiro, isso melhora o cache das camadas Docker
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o resto do código fonte e constrói o artefato
COPY src ./src
RUN mvn package -DskipTests

# Etapa de execução
FROM ibm-semeru-runtimes:open-17.0.10_7-jre

WORKDIR /app

# Copia o artefato da etapa de construção para a etapa de execução
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]