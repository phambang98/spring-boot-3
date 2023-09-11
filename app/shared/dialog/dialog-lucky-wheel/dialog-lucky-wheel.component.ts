import {Component, Input} from '@angular/core';
import {NbDialogRef} from "@nebular/theme";

@Component({
  selector: 'app-dialog-lucky-wheel',
  templateUrl: './dialog-lucky-wheel.component.html',
  styleUrls: ['./dialog-lucky-wheel.component.scss']
})
export class DialogLuckyWheelComponent {

  constructor(protected ref: NbDialogRef<DialogLuckyWheelComponent>) {
  }

  @Input() title: string;
  @Input() message: string;
  dismiss() {
    this.ref.close();
  }
}
