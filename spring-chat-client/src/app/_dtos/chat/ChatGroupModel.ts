export class ChatGroupModel {
  listUserName = []
  nameChatGroup: string


  constructor(listItems: any[], nameChatGroup: string) {
    this.listUserName = listItems;
    this.nameChatGroup = nameChatGroup;
  }
}
