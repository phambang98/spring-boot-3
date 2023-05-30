export class UserProfile {
    id: number
    email: string
    userName: string
    imageUrl: string

    constructor(id: number, email: string, userName: string, imageUrl: string){
        this.email = email
        this.userName = userName
        this.imageUrl = imageUrl
        this.id = id
    }
}
