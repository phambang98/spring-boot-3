export class ChatModel {
  chatId: number
  userId: number
  userName: string
  imageUrl: string
  blockedBy: number
  lastMsg: string = ""
  lastTimeMsg: Date
  status: string;
  chatType: string;
}
