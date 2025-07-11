# JDK21をベースにする
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Gradle wrapperとソースをまとめてコピー
COPY . .

# ビルド実行（テストはスキップする場合は -x test を追加）
RUN ./gradlew clean bootJar

# ランタイム用イメージに切り替え（軽量）
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# ビルド済jarファイルをコピー
COPY --from=build /app/build/libs/*.jar app.jar

# 実行コマンド
ENTRYPOINT ["java", "-jar", "app.jar"]
