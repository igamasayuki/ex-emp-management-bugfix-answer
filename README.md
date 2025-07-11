# ex-emp-management-bugfix-answer

Spring Boot + PostgreSQL アプリケーションの Docker 実行手順です。

## 📦 構成概要

- **Java**: Eclipse Temurin JDK 21
- **ビルドツール**: Gradle（Wrapper使用）
- **DB**: PostgreSQL 15
- **初期化SQL**: `init.sql`（プロジェクト直下）

---

## 🐳 Docker を使った起動手順

### 前提

- [Docker](https://www.docker.com/) がインストールされていること  
  （※Macの場合は [Colima](https://github.com/abiosoft/colima) + Docker CLI も可）

---

### 1. このリポジトリをクローン

```bash
git clone https://github.com/igamasayuki/ex-emp-management-bugfix-answer.git
cd ex-emp-management-bugfix-answer
```

---

### 2. 初期化SQLの配置

初期化用SQL `init.sql` はプロジェクト直下に配置済みです。

---

### 3. Dockerイメージのビルド & コンテナ起動

```bash
docker-compose up -d --build
```

※-d ・・・ バックグラウンドで起動させます  
※初回はビルドおよび依存コンテナの起動に1〜2分程度かかります。

---

### 4. アプリケーションアクセス

アプリケーションが起動したら、以下のURLにアクセス：

```
http://localhost:8080
```

---

## 💾 使用ポート

| サービス                  | ポート  |
|-----------------------|------|
| アプリケーション（Spring Boot） | 8080 |
| データベース（PostgreSQL）    | 5432 |

---

## 🔧 環境変数（`.env`不要）

環境変数は `docker-compose.yml` 内に記述済みです。

---

## 📁 その他

### データベース初期化

`init.sql` が以下の場所に自動マウントされ、コンテナ起動時に自動で実行されます：

```yaml
volumes:
  - ./init.sql:/docker-entrypoint-initdb.d/init.sql
```

---

## 🛑 停止とクリーンアップ

```bash
docker-compose down
```

キャッシュ含めて削除したい場合：

```bash
docker-compose down --volumes --rmi all
```
