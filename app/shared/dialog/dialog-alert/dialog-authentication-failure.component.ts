import {Component, Input} from '@angular/core';
import {NbDialogRef} from '@nebular/theme';
import {Router} from "@angular/router";

@Component({
  template: `
    <nb-card size="medium">
      <nb-card-header>{{ title }}</nb-card-header>
      <nb-card-body class="text-center">
        <p>
          <nb-icon icon="close-outline" status="danger" size="giant"></nb-icon>
        </p>
        <p class="message-dialog-failure">{{ message }}</p>
      </nb-card-body>
      <nb-card-footer class="text-center">
        <button nbButton (click)="dismiss()" status="primary">Close</button>
      </nb-card-footer>
    </nb-card>
  `,
  styleUrls: ['./dialog-authentication-failure.component.scss']
})
export class DialogAuthenticationFailureComponent {

  @Input() title: string;
  @Input() message: string;

  constructor(protected ref: NbDialogRef<DialogAuthenticationFailureComponent>) {
  }

  dismiss() {
    this.ref.close();
  }
}
