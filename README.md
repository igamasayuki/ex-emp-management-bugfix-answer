# ex-emp-management-bugfix-answer

Spring Boot + PostgreSQL アプリケーションの開発環境セットアップ手順です。

## 構成概要

- **Java**: Eclipse Temurin JDK 25（Gradle toolchain が自動ダウンロード）
- **ビルドツール**: Gradle
- **DB**: PostgreSQL 15（Docker で起動）
- **初期化SQL**: `db/init.sql`

---

## 開発環境のパターン

**DB のみ Docker で起動し、アプリはローカルで実行する**方式です。

```
[IDE / コマンドライン]          [Docker]
  Spring Boot アプリ   <-->   PostgreSQL
  (localhost:8080)            (localhost:5432)
```

この方式のメリット:
- コード変更のたびに Docker イメージをビルドし直す必要がない
- IDE のデバッガがそのまま使える
- 起動が速い

---

## セットアップ手順

### 前提

以下のいずれかをインストールしていること

| OS | ツール | 商用利用 |
|---|---|---|
| Mac / Windows | [Rancher Desktop](https://rancherdesktop.io/)（dockerd モードに変更） | 無料 |
| Mac | [Docker Desktop](https://www.docker.com/) | 従業員250人未満は無料 |
| Windows | [Docker Desktop](https://www.docker.com/) | 従業員250人未満は無料 |

---

### 1. このリポジトリをクローン

```bash
git clone https://github.com/igamasayuki/ex-emp-management-bugfix-answer.git
cd ex-emp-management-bugfix-answer
```

---

### 2. DB（PostgreSQL）を起動

```bash
docker compose up
```

初回は PostgreSQL イメージのダウンロードと `db/init.sql` の実行が行われます。  
`database system is ready to accept connections` のログが出れば起動完了です。

---

### 3. アプリを起動

IDE（Eclipse / IntelliJ）からメインクラスを実行するか、以下のコマンドを使います。

```bash
./gradlew bootRun
```

初回は Gradle が JDK 25 を自動ダウンロードします（数分かかる場合があります）。

---

### 4. アプリケーションアクセス

```
http://localhost:8080
```

---

## ログイン情報

| 項目 | 値 |
|---|---|
| メールアドレス | admin@example.com |
| パスワード | admin |

---

## 使用ポート

| サービス | ポート |
|---|---|
| アプリケーション（Spring Boot） | 8080 |
| データベース（PostgreSQL） | 5432 |

---

## DB コンテナの操作

| 状況 | コマンド |
|---|---|
| 起動 | `docker compose up` |
| バックグラウンド起動 | `docker compose up -d` |
| 停止 | `Ctrl + C` または `docker compose down` |
| DBのデータもリセットする | `docker compose down -v` |

---

## データベース初期化について

`db/init.sql` が以下の場所に自動マウントされ、コンテナ初回起動時に自動で実行されます。

```yaml
volumes:
  - ./db/init.sql:/docker-entrypoint-initdb.d/init.sql
```

テーブル作成・初期データ投入はすべて自動で行われます。JDK・PostgreSQL の手動インストールは不要です。
