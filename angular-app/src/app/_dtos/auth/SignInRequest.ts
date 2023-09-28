export class SignInRequest {
  userName: string
  password: string
  captchaAnswer: string

  constructor(userName: string, password: string, captchaAnswer: string) {
    this.userName = userName;
    this.password = password;
    this.captchaAnswer = captchaAnswer;
  }
}
