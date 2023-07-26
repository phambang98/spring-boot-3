export class ChatModel {
  chatId: number
  userId: number
  email: string
  userName: string
  imageUrl: string
  blockedBy: number
  lastMsg: string = ""
  lastTimeMsg: Date
  updatedAt: Date
  status: string;
  lastTimeLogin: string;
  chatType: string;

  constructor(chatId?: number, id?: number, email?: string, userName?: string, imageUrl?: string, blockedBy?: number,
              updatedAt?: string, status?: string, lastTimeMsg?: Date, chatType?: string) {
    this.chatId = chatId
    this.userId = id
    this.email = email
    this.userName = userName
    this.imageUrl = imageUrl
    this.blockedBy = blockedBy
    this.updatedAt = new Date(updatedAt)
    this.status = status
    this.lastTimeMsg = lastTimeMsg
    this.chatType = chatType
  }

  update(id: number, email: string, userName: string, imageUrl: string, blockedBy: number, updatedAt: Date) {
    this.userId = id
    this.email = email
    this.userName = userName
    this.imageUrl = imageUrl
    this.blockedBy = blockedBy
    this.updatedAt = updatedAt
  }

}
