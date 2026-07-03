import { Page } from '@playwright/test';

// TODO: 実際のHTMLに合わせてセレクターを調整すること
export class LoginPage {
  constructor(private readonly page: Page) {}

  async goto(): Promise<void> {
    await this.page.goto('/');
  }

  async login(mailAddress: string, password: string): Promise<void> {
    // login.html: <label for="mailAddress">メールアドレス:</label>
    await this.page.getByLabel('メールアドレス:').fill(mailAddress);
    // login.html: <label for="inputPassword">パスワード:</label>
    await this.page.getByLabel('パスワード:').fill(password);
    await this.page.getByRole('button', { name: 'ログイン' }).click();
  }

  async getErrorMessage(): Promise<string> {
    // login.html: <p th:text="${errorMessage}"> inside .alert-danger
    return this.page.locator('.alert-danger p').textContent() ?? '';
  }
}