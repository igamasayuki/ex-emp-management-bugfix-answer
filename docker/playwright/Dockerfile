FROM mcr.microsoft.com/playwright:v1.52.0-jammy
WORKDIR /e2e
# package.json（と存在すれば package-lock.json）をコピーして依存関係をインストール
COPY e2e/package*.json ./
RUN npm install
# テストコード一式をコピー
COPY e2e/ .
CMD ["npx", "playwright", "test"]
