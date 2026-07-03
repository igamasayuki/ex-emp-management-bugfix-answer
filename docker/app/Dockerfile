# ビルドステージ
# TODO: build.gradle の languageVersion に合わせて JDK バージョンを確認すること（現在は25）
FROM gradle:jdk25 AS builder
WORKDIR /app
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
COPY src ./src
RUN gradle bootJar --no-daemon

# 実行ステージ
FROM eclipse-temurin:25-jre
# ヘルスチェック用に curl をインストール
RUN apt-get update && apt-get install -y --no-install-recommends curl && rm -rf /var/lib/apt/lists/*
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]