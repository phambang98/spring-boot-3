import { Component, Input } from '@angular/core';
import { NbDialogRef } from '@nebular/theme';

@Component({
    template: `
    <nb-card class="dialog-card">
      <nb-card-header>Enter Your Friend User Name</nb-card-header>
      <nb-card-body>
        <input #userName nbInput placeholder="User Name" type="text">
      </nb-card-body>
      <nb-card-footer class="text-center">
        <button nbButton (click)="submit(userName.value)" status="primary" class="m-2">Submit</button>
        <button nbButton (click)="dismiss()" status="danger" class="m-2">Close</button>
      </nb-card-footer>
    </nb-card>
    `,
})
export class NewChatComponent {

    constructor(protected ref: NbDialogRef<NewChatComponent>) {
    }

    dismiss() {
        this.ref.close();
    }

    submit(userName: string){
        this.ref.close(userName);
    }
}
