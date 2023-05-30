export class MessageRequest {
  recipientId: number
  content: string
  messageId: number


  constructor(recipientId: number, content: string, messageId: number) {
    this.recipientId = recipientId
    this.content = content
    this.messageId = messageId
  }
}
