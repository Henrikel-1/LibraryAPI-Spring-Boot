# syntax=docker/dockerfile:1

# ---- Etapa de build ----
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app

# Copia só os arquivos de build primeiro, pra aproveitar cache de camada
# (dependências só são baixadas de novo se esses arquivos mudarem)
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts ./
RUN chmod +x gradlew

# Copia o código-fonte e gera o jar
COPY src src
RUN ./gradlew bootJar --no-daemon

# ---- Etapa de execução ----
FROM eclipse-temurin:25-jre AS runtime
WORKDIR /app

# Usuário não-root, por segurança
RUN addgroup --system spring && adduser --system --ingroup spring spring
USER spring:spring

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]