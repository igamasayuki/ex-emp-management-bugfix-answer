import { test, expect } from '@playwright/test';

test('管理者登録からログイン・編集・登録・ログアウトまで', async ({ page }) => {
  await page.goto('http://localhost:8080/');
  await page.waitForLoadState('networkidle');

  // 管理者登録画面へ遷移
  await page.getByRole('link', { name: '管理者登録はこちらから' }).click();

  // 管理者登録フォーム入力
  await page.getByLabel('氏名:').fill('伊賀将之');
  await page.getByLabel('メールアドレス:').fill('igamasayuki@gmail.com');
  await page.locator('#password').fill('igaigaiga');
  await page.locator('#confirmationPassword').fill('igaigaiga');

  // 管理者登録送信
  await page.getByRole('button', { name: '登録' }).click();

　// ログインページへ明示的に遷移
　await page.goto('http://localhost:8080/toLogin'); // 実際のURLに合わせて調整

  // ログイン
  await page.getByLabel('メールアドレス:').fill('igamasayuki@gmail.com');
  await page.getByLabel('パスワード:').fill('igaigaiga');
  // ログインボタンをクリック（確実に表示されてから）
  await expect(page.getByRole('button', { name: 'ログイン' })).toBeVisible();
  await page.getByRole('button', { name: 'ログイン' }).click();

　// ログインページへ明示的に遷移
　await page.goto('http://localhost:8080/employee/showList'); // 実際のURLに合わせて調整

  // 検索（氏名に「太郎」）
  await page.locator('#searchName').fill('太郎');
  await page.getByRole('button', { name: '検索' }).click();

  // 詳細画面へ
　await page.goto('http://localhost:8080/employee/showDetail?id=5'); // 実際のURLに合わせて調整

  // 編集
  await page.getByLabel('扶養人数').fill('20');
  await page.getByRole('button', { name: '更新' }).click();

  // ページネーション確認
  await page.getByRole('link', { name: '2' }).click();
  await page.getByRole('link', { name: '3' }).click();

  // 従業員登録画面へ
  await page.getByRole('link', { name: '従業員登録' }).click();
  await page.getByRole('button', { name: '登録' }).click(); // 空のまま登録テスト

  // ログアウト
  await page.getByRole('link', { name: 'ログアウト' }).click();
});
