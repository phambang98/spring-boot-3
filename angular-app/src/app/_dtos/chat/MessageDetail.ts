import {FileProfile} from "./FileProfile";

export class MessageDetail {
  messageId: number
  senderId: number
  recipientId
  chatId: number
  chatType: string
  content: string
  quote: string = ""
  files: FileProfile[] = []
  contentType: string
  imageUrl: string = ""
  senderName: string
  createdAt: Date
  reply: boolean
  latitude: number = 0
  longitude: number = 0
  read: string
  lastMsg: string

}
