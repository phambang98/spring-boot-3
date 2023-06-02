export class MessageRequest {
  recipientId: number
  content: string
  messageId: number
  chatType: string
  chatId: number

  constructor(recipientId: number, content: string, messageId: number, chatType: string, chatId: number) {
    this.recipientId = recipientId;
    this.content = content;
    this.messageId = messageId;
    this.chatType = chatType;
  }
}
