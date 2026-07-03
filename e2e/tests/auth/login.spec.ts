import { test, expect } from '@playwright/test';
import { LoginPage } from '../pages/LoginPage';

// テストデータ（後でfixturesに切り出しやすい形で定義）
const VALID_USER = {
  mailAddress: 'admin@example.com',
  password: 'admin',
};

const INVALID_USER = {
  mailAddress: 'admin@example.com',
  password: 'wrongpassword',
};

test.describe('ログイン', () => {
  test('正常系: 有効な認証情報でログインするとダッシュボードに遷移する', async ({ page }) => {
    const loginPage = new LoginPage(page);
    await loginPage.goto();
    await loginPage.login(VALID_USER.mailAddress, VALID_USER.password);

    await expect(page).toHaveURL('/employee/showList');
  });

  test('異常系: 無効なパスワードでエラーメッセージが表示される', async ({ page }) => {
    const loginPage = new LoginPage(page);
    await loginPage.goto();
    await loginPage.login(INVALID_USER.mailAddress, INVALID_USER.password);

    const errorMessage = await loginPage.getErrorMessage();
    expect(errorMessage).toBeTruthy();
  });

  test('異常系: ユーザー名が空欄でバリデーションエラーが表示される', async ({ page }) => {
    const loginPage = new LoginPage(page);
    await loginPage.goto();
    await loginPage.login('', VALID_USER.password);

    const errorMessage = await loginPage.getErrorMessage();
    expect(errorMessage).toBeTruthy();
  });
});