export class CloseDialog {
  submit = false
  data: any


  constructor(submit: boolean, data: any) {
    this.submit = submit;
    this.data = data;
  }
}
