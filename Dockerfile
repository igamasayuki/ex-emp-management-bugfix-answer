# ビルドステージ: 公式GradleイメージのJDK25版（Gradleは常に最新安定版）
FROM gradle:jdk25 AS build

WORKDIR /app

COPY . .

RUN gradle clean bootJar --no-daemon

# 実行ステージ: 軽量なJREイメージ
FROM eclipse-temurin:25-jre

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]