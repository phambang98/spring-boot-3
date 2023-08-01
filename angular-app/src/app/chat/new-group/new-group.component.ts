import {Component, Input, OnInit} from '@angular/core';
import {NbDialogRef} from '@nebular/theme';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../_services/user.service";
import {ErrorService} from "../../_services/error.service";
import {ChatGroupModel} from "../../_dtos/chat/ChatGroupModel";

@Component({
  template: `
    <nb-card class="dialog-card">
      <nb-card-header>Nhập tên nhóm mới</nb-card-header>
      <nb-card-body>
        <div>
          <input #nameChatGroup nbInput placeholder="Tên nhóm chat" type="text">
        </div>
        <div>
          <input #userName (keydown.enter)="addToListReq(userName.value)" (keyup)="keydownInput($event)" nbInput
                 placeholder="User Name" class="input-user-name" type="text">
        </div>
        <div class="col alert alert-danger" role="alert"
             *ngIf="!existsUserName && userName.value">
          Invalid Username!
        </div>
        <div class="list" [ngClass]="listItems.length == 0 ? 'displayEmpty' : ''">
          <label for="html" id="{{ i }}" *ngFor="let item of listItems; let i = index"
                 (click)="delete(i)">{{ item }}
            <nb-icon icon="close-outline"></nb-icon>
          </label>
        </div>
        <div class="col alert alert-danger" role="alert"
             *ngIf="!existsListItem">
          Không có ai
        </div>
      </nb-card-body>
      <nb-card-footer class="text-center">
        <button nbButton (click)="submit(nameChatGroup.value)" status="primary" class="m-2">
          Submit
        </button>
        <button nbButton (click)="dismiss()" status="danger" class="m-2">Close</button>
      </nb-card-footer>
    </nb-card>
  `,
  styleUrls: ['./new-group.component.scss']
})
export class NewGroupComponent implements OnInit {

  constructor(protected ref: NbDialogRef<NewGroupComponent>, private userService: UserService, private errorService: ErrorService) {
  }

  listItems = []

  ngOnInit() {

  }

  existsUserName: Boolean = true
  existsListItem: Boolean = true

  dismiss() {
    this.ref.close();
  }

  keydownInput(event) {
    this.userService.existsByUserName(event.target.value).subscribe({
      next: (v: Boolean) => {
        this.existsListItem = true
        this.existsUserName = v
      }, error: (e) => {
        this.errorService.errorFetch(e)
      }
    })
  }

  addToListReq(event) {
    if (event && this.existsUserName) {
      this.listItems.push(event);
    }
  }

  delete(item) {
    this.listItems.splice(item, 1);
  }

  submit(nameChatGroup: string) {
    if (this.listItems.length == 0) {
      this.existsListItem = false
      this.existsUserName = true
    } else {
      this.ref.close(new ChatGroupModel(this.listItems, nameChatGroup));
    }
  }
}
