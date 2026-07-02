# ex-emp-management-bugfix-answer

Spring Boot + PostgreSQL アプリケーションの Docker 実行手順です。

## 構成概要

- **Java**: Eclipse Temurin JDK 25
- **ビルドツール**: Gradle（公式Dockerイメージ使用）
- **DB**: PostgreSQL 15
- **初期化SQL**: `db/init.sql`

---

## Docker を使った起動手順

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

### 2. コンテナ起動

```bash
docker compose up --build
```

初回はイメージのビルドと依存ライブラリのダウンロードに数分かかります。  
`Started ExEmpManagement...` のログが出れば起動完了です。

---

### 3. アプリケーションアクセス

```
http://localhost:8080
```

---

## 使用ポート

| サービス | ポート |
|---|---|
| アプリケーション（Spring Boot） | 8080 |
| データベース（PostgreSQL） | 5432 |

---

## コマンドの使い分け

| 状況 | コマンド |
|---|---|
| コードを変更した | `docker compose up --build` |
| 再起動だけしたい | `docker compose up` |
| 停止する | `Ctrl + C` |
| コンテナを削除する | `docker compose down` |
| DBのデータもリセットする | `docker compose down -v` |

---

## データベース初期化について

`init.sql` が以下の場所に自動マウントされ、コンテナ初回起動時に自動で実行されます。

```yaml
volumes:
  - ./db/init.sql:/docker-entrypoint-initdb.d/init.sql
```

テーブル作成・初期データ投入はすべて自動で行われます。JDK・PostgreSQLの手動インストールは不要です。