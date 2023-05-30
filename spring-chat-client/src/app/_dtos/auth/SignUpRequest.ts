export class SignUpRequest {
  userName: string
    email: string
    password: string

    constructor(userName: string, email: string, password: string){
        this.email = email
        this.userName = userName
        this.password = password
    }
}
