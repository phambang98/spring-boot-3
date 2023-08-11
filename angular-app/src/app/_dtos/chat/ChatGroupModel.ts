export class ChatGroupModel {
  listUserName = []
  nameChatGroup: string


  constructor(listUserName: any[], nameChatGroup: string) {
    this.listUserName = listUserName
    this.nameChatGroup = nameChatGroup
  }
}
