export class UserChatGroupModel {
  listUserName = []
  chatId: number

  update(listUserName: any) {
    this.listUserName = listUserName;
  }

  constructor(listUserName: any[], chatId: number) {
    this.listUserName = listUserName;
    this.chatId = chatId;
  }
}
